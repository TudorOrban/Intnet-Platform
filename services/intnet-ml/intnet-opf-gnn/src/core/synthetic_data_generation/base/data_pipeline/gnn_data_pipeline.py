

from typing import List
import torch
import numpy as np
import networkx as nx
from torch_geometric.data import Data

from core.synthetic_data_generation.base.data_generators.sample_manager.sample_reconstructor import get_graph_data_flat_samples
from core.synthetic_data_generation.base.data_generators.sample_manager.sample_types import FixedTopologySample
from core.synthetic_data_generation.common.data_types import GridGraphData


def map_samples_to_pytorch_data(samples: List[FixedTopologySample]) -> List[Data]:
    """Prepares data for Pytorch Geometric"""

    flat_samples: List[GridGraphData] = get_graph_data_flat_samples(samples)

    return map_flat_samples_to_pytorch_data(flat_samples)

def map_flat_samples_to_pytorch_data(flat_samples: List[GridGraphData]) -> List[Data]:
    graph_data_list: List[Data] = []

    for graph_data in flat_samples:
        adj_matrix = get_adjacency_matrix(graph_data)
        node_features = extract_node_features(graph_data)
        edge_features = extract_edge_features(graph_data)
        generator_powers = extract_generator_powers(graph_data)
        
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
            generator_powers.append(generator.state.p_mw)

    return np.array(generator_powers)

def get_adjacency_matrix(graph_data: GridGraphData) -> np.ndarray:
    graph = nx.Graph()

    for edge in graph_data.edges:
        graph.add_edge(edge.src_bus_id, edge.dest_bus_id)

    adj_matrix = nx.adjacency_matrix(graph).todense()
    return np.array(adj_matrix)