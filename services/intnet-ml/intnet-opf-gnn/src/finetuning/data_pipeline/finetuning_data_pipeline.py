import numpy as np
import torch
from torch_geometric.data import Data

from core.common.data_types import GridGraphData
from core.data_pipeline.gnn_data_pipeline import calculate_normalization_parameters, extract_and_collect_features_single, normalize_features
from finetuning.common.data_types import DynamicDataRecord
from finetuning.common.dynamic_data_record_manager import update_graph_with_record


def create_pytorch_data_from_record(base_graph: GridGraphData, record: DynamicDataRecord) -> Data:
    """Creates a PyTorch Geometric Data object from a base graph and a dynamic record."""

    # Set dynamic data to graph
    updated_graph = update_graph_with_record(base_graph, record)

    # Extract features
    node_features, edge_features, generator_powers, adj_matrix = extract_and_collect_features_single(updated_graph)

    # Normalize
    node_mean, node_std, edge_mean, edge_std = calculate_normalization_parameters([node_features], [edge_features])
    normalized_node_features, normalized_edge_features = normalize_single_features(
        node_features, edge_features, node_mean, node_std, edge_mean, edge_std
    )

    # Create PyTorch Geometric Data object
    edge_index = torch.tensor(np.array(np.where(adj_matrix == 1)), dtype=torch.long)
    x = torch.tensor(normalized_node_features, dtype=torch.float)
    edge_attr = torch.tensor(normalized_edge_features, dtype=torch.float)
    y = torch.tensor(generator_powers, dtype=torch.float)

    return Data(x=x, edge_index=edge_index, edge_attr=edge_attr, y=y)

import numpy as np

def normalize_single_features(
    node_features: np.ndarray,
    edge_features: np.ndarray,
    node_mean: np.ndarray,
    node_std: np.ndarray,
    edge_mean: np.ndarray,
    edge_std: np.ndarray,
):
    node_features_to_normalize = node_features[:, 2:]
    normalized_node_features_to_normalize = (node_features_to_normalize - node_mean) / (node_std + 1e-7)
    normalized_node_features = np.concatenate([node_features[:, :2], normalized_node_features_to_normalize], axis=1)

    normalized_edge_features = (edge_features - edge_mean) / (edge_std + 1e-7)

    return normalized_node_features, normalized_edge_features