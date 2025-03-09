import random
from typing import List, Tuple
import numpy as np
import torch
from torch_geometric.data import Data

from core.common.data_types import GridGraphData
from core.data_pipeline.gnn_data_pipeline import calculate_normalization_parameters, extract_and_collect_features_single, extract_edge_features, extract_node_features, get_adjacency_matrix
from finetuning.common.data_types import DynamicDataRecord
from finetuning.common.dynamic_data_record_manager import update_graph_with_record


def preprocess_data(base_graph: GridGraphData, records: List[DynamicDataRecord]) -> Tuple[torch.Tensor, np.ndarray, np.ndarray, np.ndarray, np.ndarray]:
    """Preprocesses the data by computing edge_index and normalization parameters."""

    random.shuffle(records)

    adj_matrix = get_adjacency_matrix(update_graph_with_record(base_graph, records[0]))
    edge_index = torch.tensor(np.array(np.where(adj_matrix == 1)), dtype=torch.long)

    all_node_features = [extract_node_features(update_graph_with_record(base_graph, record)) for record in records]
    all_edge_features = [extract_edge_features(update_graph_with_record(base_graph, record)) for record in records]
    node_mean, node_std, edge_mean, edge_std = calculate_normalization_parameters(all_node_features, all_edge_features)

    num_node_features = len(all_node_features[0][0])

    return edge_index, node_mean, node_std, edge_mean, edge_std, num_node_features

def create_pytorch_data_from_record(base_graph: GridGraphData, record: DynamicDataRecord, edge_index: torch.Tensor, node_mean, node_std, edge_mean, edge_std) -> Data:
    """Creates a PyTorch Geometric Data object from a base graph and a dynamic record. - precomputed data version"""

    # Set dynamic data to graph
    updated_graph = update_graph_with_record(base_graph, record)

    # Extract features
    node_features, edge_features, generator_powers = extract_and_collect_features_single(updated_graph)

    # Normalize
    normalized_node_features, normalized_edge_features = normalize_single_features(
        node_features, edge_features, node_mean, node_std, edge_mean, edge_std
    )

    # Create PyTorch Geometric Data object
    x = torch.tensor(normalized_node_features, dtype=torch.float)
    edge_attr = torch.tensor(normalized_edge_features, dtype=torch.float)
    y = torch.tensor(generator_powers, dtype=torch.float)

    return Data(x=x, edge_index=edge_index, edge_attr=edge_attr, y=y)

def normalize_single_features(
    node_features: np.ndarray,
    edge_features: np.ndarray,
    node_mean: np.ndarray,
    node_std: np.ndarray,
    edge_mean: np.ndarray,
    edge_std: np.ndarray,
):
    epsilon = 1e-7

    node_features_to_normalize = node_features[:, 2:]
    normalized_node_features_to_normalize = (node_features_to_normalize - node_mean) / (node_std + epsilon)
    normalized_node_features = np.concatenate([node_features[:, :2], normalized_node_features_to_normalize], axis=1)

    normalized_edge_features = (edge_features - edge_mean) / (edge_std + epsilon)

    return normalized_node_features, normalized_edge_features