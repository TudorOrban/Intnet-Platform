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
        self.dropout_rate = dropout_rate
        
    def forward(self, data):
        x, edge_index = data.x, data.edge_index

        x = self.conv1(x, edge_index)
        x = F.relu(x)
        x = F.dropout(x, p=self.dropout_rate, training=self.training)
        x = self.conv2(x, edge_index)

        generator_mask = (data.x[:, 0] == 1)
        generator_outputs = x[generator_mask].squeeze(1)
        
        return generator_outputs
    
def train_gnn(input_data: List[Data], epochs=100, hidden_channels=64, lr=0.01, weight_decay=1e-5, dropout_rate=0.4, patience=10):
    """Trains the GNN model."""

    train_test_ratio = 0.8
    train_size = int(train_test_ratio * len(input_data))
    train_data = input_data[:train_size]
    val_data = input_data[train_size:]

    model = BaseGNN(num_node_features=train_data[0].x.shape[1], hidden_channels=hidden_channels, dropout_rate=dropout_rate)
    optimizer = torch.optim.Adam(model.parameters(), lr, weight_decay=weight_decay)
    criterion = torch.nn.MSELoss()
    scheduler = torch.optim.lr_scheduler.ReduceLROnPlateau(optimizer, "min", patience=patience // 2, factor=0.5, verbose=True)
    
    best_val_loss = float("inf")
    epochs_no_improve = 0

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
        scheduler.step(avg_val_loss)

        print(f"Epoch {epoch + 1}, Train Loss: {avg_train_loss}, Val Loss: {avg_val_loss}")
        
        mlflow.log_metric("train_loss", avg_train_loss, step=epoch)
        mlflow.log_metric("val_loss", avg_val_loss, step=epoch)

        if avg_val_loss < best_val_loss:
            best_val_loss = avg_val_loss
            epochs_no_improve = 0
            torch.save(model.state_dict(), "best_model.pth")
        else:
            epochs_no_improve += 1
            if epochs_no_improve == patience:
                print("Early stopping!")
                break
        model.train()

    model.load_state_dict(torch.load("best_model.pth"))
    return model