

import random
from typing import List
import torch
import numpy as np
import networkx as nx
from torch_geometric.data import Data

from core.data_generators.sample_manager.sample_reconstructor import get_graph_data_flat_samples
from core.data_generators.sample_manager.sample_types import FixedTopologySample
from core.common.data_types import GridGraphData


def map_samples_to_pytorch_data(samples: List[FixedTopologySample]) -> List[Data]:
    """Prepares data for Pytorch Geometric"""

    flat_samples: List[GridGraphData] = get_graph_data_flat_samples(samples)

    return map_flat_samples_to_pytorch_data(flat_samples)


def map_flat_samples_to_pytorch_data(flat_samples: List[GridGraphData]) -> List[Data]:
    """Prepares flat data for Pytorch Geometric"""

    # Shuffle the samples
    random.shuffle(flat_samples)

    node_features_list, edge_features_list, generator_powers_list, adj_matrices_list = extract_and_collect_features(flat_samples)

    node_mean, node_std, edge_mean, edge_std = calculate_normalization_parameters(
        node_features_list, edge_features_list
    )

    normalized_node_features_list, normalized_edge_features_list = normalize_features(
        node_features_list, edge_features_list, node_mean, node_std, edge_mean, edge_std
    )

    graph_data_list = create_pytorch_data_list(
        flat_samples,
        normalized_node_features_list,
        normalized_edge_features_list,
        generator_powers_list,
        adj_matrices_list,
    )

    return graph_data_list


def extract_and_collect_features(flat_samples: List[GridGraphData]):
    node_features_list = []
    edge_features_list = []
    generator_powers_list = []
    adj_matrices_list = []

    for graph_data in flat_samples:
        node_features_list.append(extract_node_features(graph_data))
        edge_features_list.append(extract_edge_features(graph_data))
        generator_powers_list.append(extract_generator_powers(graph_data))
        adj_matrices_list.append(get_adjacency_matrix(graph_data))

    return node_features_list, edge_features_list, generator_powers_list, adj_matrices_list

def extract_and_collect_features_single(sample: GridGraphData):
    node_features = extract_node_features(sample)
    edge_features = extract_edge_features(sample)
    generator_powers = extract_generator_powers(sample)

    return node_features, edge_features, generator_powers

def calculate_normalization_parameters(node_features_list: List[np.ndarray], edge_features_list: List[np.ndarray]):
    all_node_features = np.concatenate(node_features_list, axis=0)
    all_node_features_to_normalize = all_node_features[:, 2:]
    all_edge_features = np.concatenate(edge_features_list, axis=0)

    node_mean = np.mean(all_node_features_to_normalize, axis=0)
    node_std = np.std(all_node_features_to_normalize, axis=0)
    edge_mean = np.mean(all_edge_features, axis=0)
    edge_std = np.std(all_edge_features, axis=0)

    return node_mean, node_std, edge_mean, edge_std


def normalize_features(
    node_features_list: List[np.ndarray],
    edge_features_list: List[np.ndarray],
    node_mean: np.ndarray,
    node_std: np.ndarray,
    edge_mean: np.ndarray,
    edge_std: np.ndarray,
):
    normalized_node_features_list = []
    normalized_edge_features_list = []

    for node_features in node_features_list:
        node_features_to_normalize = node_features[:, 2:]
        normalized_node_features_to_normalize = (node_features_to_normalize - node_mean) / (node_std + 1e-7)
        normalized_node_features = np.concatenate([node_features[:, :2], normalized_node_features_to_normalize], axis=1)
        normalized_node_features_list.append(normalized_node_features)

    for edge_features in edge_features_list:
        normalized_edge_features = (edge_features - edge_mean) / (edge_std + 1e-7)
        normalized_edge_features_list.append(normalized_edge_features)

    return normalized_node_features_list, normalized_edge_features_list


def create_pytorch_data_list(
    flat_samples: List[GridGraphData],
    normalized_node_features_list: List[np.ndarray],
    normalized_edge_features_list: List[np.ndarray],
    generator_powers_list: List[np.ndarray],
    adj_matrices_list: List[np.ndarray],
):
    graph_data_list = []

    for i, _ in enumerate(flat_samples):
        adj_matrix = adj_matrices_list[i]
        node_features = normalized_node_features_list[i]
        edge_features = normalized_edge_features_list[i]
        generator_powers = generator_powers_list[i]

        x = torch.tensor(node_features, dtype=torch.float)
        edge_index = torch.tensor(np.array(np.where(adj_matrix == 1)), dtype=torch.long)
        edge_attr = torch.tensor(edge_features, dtype=torch.float)
        y = torch.tensor(generator_powers, dtype=torch.float)

        data = Data(x=x, edge_index=edge_index, edge_attr=edge_attr, y=y)

        graph_data_list.append(data)

    return graph_data_list


def extract_node_features(graph_data: GridGraphData) -> np.ndarray:
    node_features = []

    for bus in graph_data.buses:
        gen_present = 1.0 if bus.generators else 0.0
        load_present = 1.0 if bus.loads else 0.0
        vm_pu = bus.state.vm_pu

        total_gen_min_p_mw = 0.0
        total_gen_max_p_mw = 0.0
        total_gen_min_q_mvar = 0.0
        total_gen_max_q_mvar = 0.0
        if gen_present == 1.0:
            total_gen_min_p_mw = sum(gen.min_p_mw for gen in bus.generators)
            total_gen_max_p_mw = sum(gen.max_p_mw for gen in bus.generators)
            total_gen_min_q_mvar = sum(gen.min_q_mvar for gen in bus.generators)
            total_gen_max_q_mvar = sum(gen.max_q_mvar for gen in bus.generators)
            
        total_load_p_mw = 0
        total_load_q_mvar = 0.0
        if load_present == 1.0:
            total_load_p_mw = sum(load.state.p_mw for load in bus.loads)
            total_load_q_mvar = sum(load.state.q_mvar for load in bus.loads)

        node_features.append([
            gen_present, load_present, vm_pu, 
            total_gen_min_p_mw, total_gen_max_p_mw, total_gen_min_q_mvar, total_gen_max_q_mvar,
            total_load_p_mw, total_load_q_mvar
        ])

    return np.array(node_features)


def extract_edge_features(graph_data: GridGraphData) -> np.ndarray:
    edge_features = []

    for edge in graph_data.edges:
        length_km = edge.length_km
        r_ohm_per_km = edge.r_ohm_per_km
        x_ohm_per_km = edge.x_ohm_per_km

        edge_features.append([
            length_km, r_ohm_per_km, x_ohm_per_km
        ])

    return np.array(edge_features)

def extract_generator_powers(graph_data: GridGraphData) -> np.ndarray:
    generator_powers = []
    for bus in graph_data.buses:
        for generator in bus.generators:
            generator_powers.extend([generator.state.p_mw, generator.state.q_mvar])

    return np.array(generator_powers)

def get_adjacency_matrix(graph_data: GridGraphData) -> np.ndarray:
    graph = nx.Graph()

    for edge in graph_data.edges:
        graph.add_edge(edge.src_bus_id, edge.dest_bus_id)

    adj_matrix = nx.adjacency_matrix(graph).todense()
    return np.array(adj_matrix)