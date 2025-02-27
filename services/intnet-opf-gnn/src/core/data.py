import torch
from torch_geometric.data import Data


def generate_synthetic_graph(num_nodes: int = 10) -> Data:
    x: torch.Tensor = torch.randn(num_nodes, 1)

    edge_index: torch.Tensor = torch.randint(0, num_nodes, (2, num_nodes * 2))

    y: torch.Tensor = torch.zeros(num_nodes, 1)
    for i in range(num_nodes):
        neighbors: torch.Tensor = edge_index[1, edge_index[0] == i]
        neighbor_features: torch.Tensor = x[neighbors]
        y[i] = x[i] + torch.mean(neighbor_features) if len(neighbor_features) > 0 else x[i]

    return Data(x=x, edge_index=edge_index, y=y)

if __name__ == "__main__":
    graph: Data = generate_synthetic_graph()
    print("Graph", graph)
