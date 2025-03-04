from typing import List
import mlflow
import torch
import torch.nn.functional as F
from torch_geometric.nn import GCNConv
from torch_geometric.data import Data

class BaseGNN(torch.nn.Module):
    def __init__(self, num_node_features, hidden_channels=64, dropout_rate=0.5):
        super(BaseGNN, self).__init__()
        self.conv1 = GCNConv(num_node_features, hidden_channels)
        self.conv2 = GCNConv(hidden_channels, 1)
        
    def forward(self, data):
        x, edge_index = data.x, data.edge_index

        x = self.conv1(x, edge_index)
        x = F.relu(x)
        x = self.conv2(x, edge_index)

        generator_mask = (data.x[:, 0] == 1)

        generator_outputs = x[generator_mask].squeeze(1)
        
        return generator_outputs
    
def train_gnn(input_data: List[Data], epochs=100, hidden_channels=64, lr=0.01):
    """Trains the GNN model."""

    train_test_ratio = 0.8
    train_size = int(train_test_ratio * len(input_data))
    train_data = input_data[:train_size]
    val_data = input_data[train_size:]

    model = BaseGNN(num_node_features=train_data[0].x.shape[1], hidden_channels=hidden_channels)
    optimizer = torch.optim.Adam(model.parameters(), lr)
    criterion = torch.nn.MSELoss()
    
    model.train()
    for epoch in range(epochs):
        total_train_loss = 0
        for data in train_data:
            optimizer.zero_grad()
            out = model(data)
            loss = criterion(out, data.y)
            loss.backward()
            optimizer.step()
            total_train_loss += loss.item()

        model.eval()
        total_val_loss = 0
        with torch.no_grad():
            for data in val_data:
                out = model(data)
                loss = criterion(out, data.y)
                total_val_loss += loss.item()

        avg_train_loss = total_train_loss / len(train_data)
        avg_val_loss = total_val_loss / len(val_data)

        print(f"Epoch {epoch + 1}, Train Loss: {avg_train_loss}, Val Loss: {avg_val_loss}")
        
        mlflow.log_metric("train_loss", avg_train_loss, step=epoch)
        mlflow.log_metric("val_loss", avg_val_loss, step=epoch)

    return model