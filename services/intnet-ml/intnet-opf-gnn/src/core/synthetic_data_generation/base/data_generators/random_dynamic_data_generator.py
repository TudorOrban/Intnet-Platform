import random
from core.data_types import GridGraphData


def generate_random_dynamic_data(graph_data: GridGraphData) -> GridGraphData:
    """Generates realistic random dynamic electric data for the electric grid components."""

    graph_data = generate_random_load_dynamic_data(graph_data)
    graph_data = generate_random_generator_dynamic_data(graph_data)
    graph_data = generate_random_bus_dynamic_data(graph_data)
    graph_data = generate_random_edge_dynamic_data(graph_data)

    return graph_data

def generate_random_load_dynamic_data(graph_data: GridGraphData) -> GridGraphData:
    for bus in graph_data.buses:
        for load in bus.loads:
            load.state.p_mw = random.uniform(load.min_p_mw, load.max_p_mw)
            load.state.q_mvar = random.uniform(load.min_q_mvar, load.max_q_mvar)
    return graph_data

def generate_random_generator_dynamic_data(graph_data: GridGraphData) -> GridGraphData:
    for bus in graph_data.buses:
        for gen in bus.generators:
            gen.state.p_mw = random.uniform(gen.min_p_mw, gen.max_p_mw)
            gen.state.q_mvar = random.uniform(gen.min_q_mvar, gen.max_q_mvar)
    return graph_data

def generate_random_bus_dynamic_data(graph_data: GridGraphData) -> GridGraphData:
    for bus in graph_data.buses:
        bus.state.vm_pu = random.uniform(bus.min_vm_pu, bus.max_vm_pu)
        bus.state.va_deg = random.uniform(-180, 180)
        bus.state.p_inj_mw = sum(gen.state.p_mw for gen in bus.generators) - sum(load.state.p_mw for load in bus.loads)
        bus.state.q_inj_mvar = sum(gen.state.q_mvar for gen in bus.generators) - sum(load.state.q_mvar for load in bus.loads)
        bus.state.tap_pos = random.uniform(0.9, 1.1) if bus.generators else None
    return graph_data

def generate_random_edge_dynamic_data(graph_data: GridGraphData) -> GridGraphData:
    for edge in graph_data.edges:
        edge.state.p_flow_mw = random.uniform(-100, 100)
        edge.state.q_flow_mvar = random.uniform(-50, 50)
        edge.state.i_ka = random.uniform(0, 1.0)
        edge.state.in_service = random.choice([True, False])
    return graph_data