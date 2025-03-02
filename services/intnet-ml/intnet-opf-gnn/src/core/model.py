
from typing import List
import torch
from torch_geometric.data import Data
import torch.nn.functional as F
from torch_geometric.nn import GCNConv
from torch_geometric.data import Data
import numpy as np

from core.synthetic_data import GridSample


def prepare_data_for_gnn(samples: List[GridSample]) -> List[Data]:
    """Prepares data for Pytorch Geometric"""

    graph_data_list: List[Data] = []

    for sample in samples:
        adj_matrix = sample.adj_matrix
        edge_index = torch.tensor(np.array(np.where(adj_matrix == 1)), dtype=torch.long)
        x = torch.tensor(sample.node_features, dtype=torch.float)
        y = torch.tensor([sample.gen_p_mw], dtype=torch.float)
        data = Data(x=x, edge_index=edge_index, y=y)

        print(f"edge_index shape: {edge_index.shape}, edge_index: {edge_index}")
        print(f"x shape: {x.shape}")
              
        graph_data_list.append(data)

    return graph_data_list

class SimpleGCN(torch.nn.Module):
    def __init__(self, num_node_features):
        super(SimpleGCN, self).__init__()
        self.conv1 = GCNConv(num_node_features, 32)
        self.conv2 = GCNConv(32, 1)

    def forward(self, data):
        x, edge_index = data.x, data.edge_index

        if edge_index.numel() == 0:
            return torch.zeros(1, 1) 
        
        x = self.conv1(x, edge_index)
        x = F.relu(x)
        x = self.conv2(x, edge_index)
        x = torch.mean(x, dim=0)
        return x
    
def train_gnn(data_list: List[Data], epochs=100, lr=0.01):
    """Trains the GNN model."""
    model = SimpleGCN(num_node_features=data_list[0].x.shape[1])
    optimizer = torch.optim.Adam(model.parameters(), lr)
    criterion = torch.nn.MSELoss()

    model.train()
    for epoch in range(epochs):
        total_loss = 0
        for data in data_list:
            optimizer.zero_grad()
            out = model(data)
            loss = criterion(out, data.y)
            loss.backward()
            optimizer.step()
            total_loss += loss.item()
        
        if (epoch + 1) % 10 == 0:
            print(f"Epoch {epoch + 1}, Loss: {total_loss / len(data_list)}")
    
    return model
