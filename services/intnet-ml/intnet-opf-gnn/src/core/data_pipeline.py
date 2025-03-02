
from typing import List
import torch
from torch_geometric.data import Data
import numpy as np
from core.synthetic_data import GridSample


def prepare_data_for_gnn(samples: List[GridSample]) -> List[Data]:
    """Prepares data for Pytorch Geometric"""

    graph_data_list: List[Data] = []

    # Extract float features
    vm_pu_values = np.array([sample.node_features[:, 3] for sample in samples]).flatten()
    load_power_values = np.array([sample.node_features[:, 4] for sample in samples]).flatten()
    gen_p_mw_values = np.array([sample.gen_p_mw for sample in samples])

    # Calculate normalization parameters
    mean_vm_pu = np.mean(vm_pu_values)
    std_vm_pu = np.std(vm_pu_values)
    mean_load_power = np.mean(load_power_values)
    std_load_power = np.std(load_power_values)
    mean_gen_p_mw = np.mean(gen_p_mw_values)
    std_gen_p_mw = np.std(gen_p_mw_values)

    epsilon = 1e-7

    # Normalize and transform to Pytorch Geometric Data
    for sample in samples:
        adj_matrix = sample.adj_matrix
        edge_index = torch.tensor(np.array(np.where(adj_matrix == 1)), dtype=torch.long)

        normalized_vm_pu = (sample.node_features[:, 3] - mean_vm_pu) / (std_vm_pu if std_vm_pu != 0 else epsilon)
        normalized_load_power = (sample.node_features[:, 4] - mean_load_power) / (std_load_power if std_load_power != 0 else epsilon)

        normalized_features = np.copy(sample.node_features)
        normalized_features[:, 3] = normalized_vm_pu
        normalized_features[:, 4] = normalized_load_power

        x = torch.tensor(normalized_features, dtype=torch.float)
        y = torch.tensor([(sample.gen_p_mw - mean_gen_p_mw) / (std_gen_p_mw if std_gen_p_mw != 0 else epsilon)], dtype=torch.float)

        data = Data(x=x, edge_index=edge_index, y=y)

        # print(f"edge_index shape: {edge_index.shape}, edge_index: {edge_index}")
        # print(f"x shape: {x.shape}")
              
        graph_data_list.append(data)

    return graph_data_list