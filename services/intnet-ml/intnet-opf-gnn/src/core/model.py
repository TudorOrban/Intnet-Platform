
from dataclasses import dataclass
from typing import List
import pandapower as pp
import networkx as nx
import torch
from torch_geometric.data import Data
import torch.nn.functional as F
from torch_geometric.nn import GCNConv
from torch_geometric.data import Data
import numpy as np

@dataclass
class GridSample:
    load_p_mw: list[float]
    gen_p_mw: float
    adj_matrix: np.ndarray
    node_features: np.ndarray


def prepare_data_for_gnn(samples: List[GridSample]) -> List[Data]:
    """Prepares data for Pytorch Geometric"""

    graph_data_list: List[Data] = []

    for sample in samples:
        edge_index = torch.tensor(np.array(np.where(sample.adj_matrix == 1)), dtype=torch.long)
        x = torch.tensor(sample.node_features, dtype=torch.float)
        y = torch.tensor([sample.gen_p_mw], dtype=torch.float)
        data = Data(x, edge_index, y)
        graph_data_list.append(data)

    return graph_data_list

class SimpleGCN(torch.nn.Module):
    def __init__(self, num_node_features):
        super(SimpleGCN, self).__init__()
        self.conv1 = GCNConv(num_node_features, 16)
        self.conv2 = GCNConv(16, 1)

    def forward(self, data):
        x, edge_index = data.x, data.edge_index
        x = self.conv1(x, edge_index)
        x = F.relu(x)
        x = self.conv2(x, edge_index)
        x = torch.mean(x, dim=0, keepdim=True)
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

def generate_synthetic_data(num_samples=10) -> List[GridSample]:
    """Generates data points for GNN training."""
    
    data: List[GridSample] = []

    for _ in range(num_samples):
        p_mw_load_1 = np.random.uniform(2, 30)
        p_mw_load_2 = np.random.uniform(2, 30)
        p_mw_load_3 = np.random.uniform(2, 30)

        net = generate_random_network(p_mw_load_1, p_mw_load_2, p_mw_load_3)

        pp.runopp(net)

        load_p_mw = [p_mw_load_1, p_mw_load_2, p_mw_load_3]
        gen_p_mw = net.res_gen.p_mw.values[0]

        graph = pp.topology.create_nxgraph(net)
        adj_matrix = nx.adjacency_matrix(graph)
        adj_matrix = adj_matrix.todense()
        adj_matrix = np.array(adj_matrix)

        node_features = []
        for bus_idx in net.bus.index:
            bus_type = 1.0 if net.bus.at[bus_idx, "type"] == "b" else 0.0
            load_present = 1.0 if bus_idx in net.load.bus.values else 0.0
            gen_present = 1.0 if bus_idx in net.gen.bus.values else 0.0
            node_features.append([bus_type, load_present, gen_present])
        node_features = np.array(node_features)

        sample = GridSample(load_p_mw, gen_p_mw, adj_matrix, node_features)
        data.append(sample)

    print(data)
    return data

def generate_random_network(p_mw_load_1: float, p_mw_load_2: float, p_mw_load_3: float):
    """Generates a random pandapower network that converges."""

    net = pp.create_empty_network()

    # Buses
    bus1 = pp.create_bus(net, vn_kv=20., type="b", min_vm_pu=0.9, max_vm_pu=1.1)
    bus2 = pp.create_bus(net, vn_kv=20., type="b", min_vm_pu=0.9, max_vm_pu=1.1)
    bus3 = pp.create_bus(net, vn_kv=20., type="b", min_vm_pu=0.9, max_vm_pu=1.1)
    bus4 = pp.create_bus(net, vn_kv=20., type="b", min_vm_pu=0.9, max_vm_pu=1.1)

    # Lines
    pp.create_line(net, from_bus=bus1, to_bus=bus2, length_km=5., std_type="NAYY 4x150 SE")
    pp.create_line(net, from_bus=bus1, to_bus=bus3, length_km=10., std_type="NAYY 4x150 SE")
    pp.create_line(net, from_bus=bus1, to_bus=bus4, length_km=8., std_type="NAYY 4x150 SE")

    # Generators
    # (Slack)
    pp.create_gen(net, bus=bus1, p_mw=5., min_p_mw=0., max_p_mw=80., q_mvar=0., max_q_mvar=100., slack=True)
    pp.create_poly_cost(net, element=0, et="gen", cp1_eur_per_mw=10.) 
    
    # Loads
    pp.create_load(net, bus=bus2, p_mw=p_mw_load_1, q_mvar=2.)
    pp.create_load(net, bus=bus3, p_mw=p_mw_load_2, q_mvar=2.)
    pp.create_load(net, bus=bus4, p_mw=p_mw_load_3, q_mvar=3.)

    return net


samples = generate_synthetic_data(10)
graph_data = prepare_data_for_gnn(samples)
trained_model = train_gnn(graph_data)