import torch
import torch.nn.functional as F
from torch_geometric.nn import GCNConv

class OpfGNN(torch.nn.Module):
    def __init__(self, num_node_features, hidden_channels=64, dropout_rate=0.5):
        super(OpfGNN, self).__init__()
        self.conv1 = GCNConv(num_node_features, hidden_channels)
        self.conv2 = GCNConv(hidden_channels, 2)
        self.dropout_rate = dropout_rate
        
    def forward(self, data):
        x, edge_index = data.x, data.edge_index

        x = self.conv1(x, edge_index)
        x = F.relu(x)
        x = F.dropout(x, p=self.dropout_rate, training=self.training)
        x = self.conv2(x, edge_index)

        generator_mask = (data.x[:, 0] == 1)
        generator_outputs = x[generator_mask]
        generator_outputs = torch.flatten(generator_outputs)
        
        return generator_outputs
    