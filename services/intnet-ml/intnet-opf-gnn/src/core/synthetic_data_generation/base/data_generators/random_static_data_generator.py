
import random
from core.data_types import EdgeType, GridGraphData


def generate_random_static_data(graph_data: GridGraphData) -> GridGraphData:
    """Generates realistic random static electric data for the electric grid components"""

    graph_data = generate_random_load_data(graph_data)
    graph_data = generate_random_generator_data(graph_data)
    graph_data = generate_random_bus_data(graph_data)
    graph_data = generate_random_edge_data(graph_data)

    return graph_data

def generate_random_load_data(graph_data: GridGraphData) -> GridGraphData:
    for bus in graph_data.buses:
        for load in bus.loads:
            load.min_p_mw = 0
            load.max_p_mw = random.uniform(1, 20)
            load.min_q_mvar = -random.uniform(1, 5)
            load.max_q_mvar = random.uniform(1, 5)
            
    return graph_data

def generate_random_generator_data(graph_data: GridGraphData) -> GridGraphData:
    available_buses = [bus for bus in graph_data.buses if bus.generators]
    if not available_buses:
        return graph_data
    
    total_generators = sum(len(bus.generators) for bus in available_buses)
    if total_generators <= 0:
        return graph_data
    
    total_load_max_p_mw = sum(load.max_p_mw for bus in graph_data.buses for load in bus.loads)
    total_load_min_q_mvar = sum(load.min_q_mvar for bus in graph_data.buses for load in bus.loads)
    total_load_max_q_mvar = sum(load.max_q_mvar for bus in graph_data.buses for load in bus.loads)

    safety_margin = 2
    variation = 0

    max_p_mw_per_gen = determine_generator_specification(total_load_max_p_mw, safety_margin, total_generators)
    min_q_mvar_per_gen = determine_generator_specification(total_load_min_q_mvar, safety_margin, total_generators)
    max_q_mvar_per_gen = determine_generator_specification(total_load_max_q_mvar, safety_margin, total_generators)
    
    for bus in available_buses:
        for gen in bus.generators:
            gen.min_p_mw = 0
            gen.max_p_mw = random.uniform(max_p_mw_per_gen * (1 - variation), max_p_mw_per_gen * (1 + variation))
            gen.min_q_mvar = random.uniform(min_q_mvar_per_gen * (1 - variation), min_q_mvar_per_gen * (1 + variation))
            gen.max_q_mvar = random.uniform(max_q_mvar_per_gen * (1 - variation), max_q_mvar_per_gen * (1 + variation))
    return graph_data

def determine_generator_specification(specification: float, safety_margin: float, total_generators: int) -> float:
    return specification * safety_margin / total_generators

def generate_random_bus_data(graph_data: GridGraphData) -> GridGraphData:
    for bus in graph_data.buses:
        bus.vn_kv = 110
        bus.min_vm_pu = 0.9
        bus.max_vm_pu = 1.1
    return graph_data

def generate_random_edge_data(graph_data: GridGraphData) -> GridGraphData:
    for edge in graph_data.edges:
        if edge.edge_type == EdgeType.TRANSMISSION_LINE:
            edge.r_ohm_per_km = random.uniform(0.01, 0.1)
            edge.x_ohm_per_km = random.uniform(0.1, 0.5)
            edge.length_km = random.uniform(1, 3)
        elif edge.edge_type == EdgeType.DISTRIBUTION_LINE:
            edge.r_ohm_per_km = random.uniform(0.1, 1.0)
            edge.x_ohm_per_km = random.uniform(0.05, 0.2)
            edge.length_km = random.uniform(1, 20)
        elif edge.edge_type == EdgeType.TRANSFORMER:
            edge.r_ohm_per_km = random.uniform(0.001, 0.01)
            edge.x_ohm_per_km = random.uniform(0.01, 0.05)
            edge.length_km = 0
    return graph_data