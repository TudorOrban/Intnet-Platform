import torch
import torch.nn.functional as F
from torch_geometric.nn import GCNConv

class SpecializedGNN(torch.nn.Module):
    def __init__(self, num_node_features, hidden_channels=64, dropout_rate=0.5):
        super(SpecializedGNN, self).__init__()
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
    

# class SpecializedGNN(torch.nn.Module):
#     def __init__(self, num_node_features, hidden_channels=64, dropout_rate=0.5):
#         super(SpecializedGNN, self).__init__()
#         self.conv1 = GCNConv(num_node_features, hidden_channels)
#         self.conv2 = GCNConv(hidden_channels, hidden_channels)
#         self.conv3 = GCNConv(hidden_channels, 1)
#         self.dropout_rate = dropout_rate
        
#     def forward(self, data):
#         x, edge_index = data.x, data.edge_index

#         x = self.conv1(x, edge_index)
#         x = F.relu(x)
#         x = F.dropout(x, p=self.dropout_rate, training=self.training)
        
#         x = self.conv2(x, edge_index)
#         x = F.relu(x)
#         x = F.dropout(x, p=self.dropout_rate, training=self.training)

#         x = self.conv3(x, edge_index)

#         generator_mask = (data.x[:, 0] == 1)
#         generator_outputs = x[generator_mask].squeeze(1)
        
#         return generator_outputs
    