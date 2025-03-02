
from typing import List
import torch
from torch_geometric.data import Data
import numpy as np
from core.synthetic_data import GridSample


def prepare_data_for_gnn(samples: List[GridSample]) -> List[Data]:
    """Prepares data for Pytorch Geometric"""

    graph_data_list: List[Data] = []

    # Extract node float features
    vm_pu_values = np.array([sample.node_features[:, 3] for sample in samples]).flatten()
    load_power_values = np.array([sample.node_features[:, 4] for sample in samples]).flatten()
    gen_p_mw_values = np.array([sample.gen_p_mw for sample in samples])
    load_q_mvar_values = np.array([sample.node_features[:, 5] for sample in samples]).flatten()
    gen_max_p_values = np.array([sample.node_features[:, 6] for sample in samples]).flatten()
    gen_min_q_values = np.array([sample.node_features[:, 7] for sample in samples]).flatten()
    gen_max_q_values = np.array([sample.node_features[:, 8] for sample in samples]).flatten()

    # Extract edge float features
    r_ohm_per_km_values = np.array([sample.edge_features[:, 0] for sample in samples]).flatten()
    x_ohm_per_km_values = np.array([sample.edge_features[:, 1] for sample in samples]).flatten()

    # Calculate normalization parameters for node features
    mean_vm_pu = np.mean(vm_pu_values)
    std_vm_pu = np.std(vm_pu_values)
    mean_load_power = np.mean(load_power_values)
    std_load_power = np.std(load_power_values)
    mean_gen_p_mw = np.mean(gen_p_mw_values)
    std_gen_p_mw = np.std(gen_p_mw_values)
    mean_load_q_mvar = np.mean(load_q_mvar_values)
    std_load_q_mvar = np.std(load_q_mvar_values)
    mean_gen_max_p = np.mean(gen_max_p_values)
    std_gen_max_p = np.std(gen_max_p_values)
    mean_gen_min_q = np.mean(gen_min_q_values)
    std_gen_min_q = np.std(gen_min_q_values)
    mean_gen_max_q = np.mean(gen_max_q_values)
    std_gen_max_q = np.std(gen_max_q_values)

    # Calculate normalization parameters for edge features
    mean_r_ohm_per_km = np.mean(r_ohm_per_km_values)
    std_r_ohm_per_km = np.std(r_ohm_per_km_values)
    mean_x_ohm_per_km = np.mean(x_ohm_per_km_values)
    std_x_ohm_per_km = np.std(x_ohm_per_km_values)

    epsilon = 1e-7

    # Normalize and transform to Pytorch Geometric Data
    for sample in samples:
        adj_matrix = sample.adj_matrix
        edge_index = torch.tensor(np.array(np.where(adj_matrix == 1)), dtype=torch.long)

        normalized_vm_pu = (sample.node_features[:, 3] - mean_vm_pu) / (std_vm_pu if std_vm_pu != 0 else epsilon)
        normalized_load_power = (sample.node_features[:, 4] - mean_load_power) / (std_load_power if std_load_power != 0 else epsilon)
        normalized_load_q_mvar = (sample.node_features[:, 5] - mean_load_q_mvar) / (std_load_q_mvar if std_load_q_mvar != 0 else epsilon)
        normalized_gen_max_p = (sample.node_features[:, 6] - mean_gen_max_p) / (std_gen_max_p if std_gen_max_p != 0 else epsilon)
        normalized_gen_min_q = (sample.node_features[:, 7] - mean_gen_min_q) / (std_gen_min_q if std_gen_min_q != 0 else epsilon)
        normalized_gen_max_q = (sample.node_features[:, 8] - mean_gen_max_q) / (std_gen_max_q if std_gen_max_q != 0 else epsilon)

        normalized_features = np.copy(sample.node_features)
        normalized_features[:, 3] = normalized_vm_pu
        normalized_features[:, 4] = normalized_load_power
        normalized_features[:, 5] = normalized_load_q_mvar
        normalized_features[:, 6] = normalized_gen_max_p
        normalized_features[:, 7] = normalized_gen_min_q
        normalized_features[:, 8] = normalized_gen_max_q

        x = torch.tensor(normalized_features, dtype=torch.float)
        y = torch.tensor([(sample.gen_p_mw - mean_gen_p_mw) / (std_gen_p_mw if std_gen_p_mw != 0 else epsilon)], dtype=torch.float)

        normalized_r_ohm_per_km = (sample.edge_features[:, 0] - mean_r_ohm_per_km) / (std_r_ohm_per_km if std_r_ohm_per_km != 0 else epsilon)
        normalized_x_ohm_per_km = (sample.edge_features[:, 1] - mean_x_ohm_per_km) / (std_x_ohm_per_km if std_x_ohm_per_km != 0 else epsilon)

        normalized_edge_features = np.stack((normalized_r_ohm_per_km, normalized_x_ohm_per_km), axis=1)
        edge_attr = torch.tensor(normalized_edge_features, dtype=torch.float)
        # print(f"edge_index shape: {edge_index.shape}, edge_index: {edge_index}")
        # print(f"x shape: {x.shape}")
              
        data = Data(x=x, edge_index=edge_index, y=y, edge_attr=edge_attr)

        graph_data_list.append(data)

    return graph_data_list