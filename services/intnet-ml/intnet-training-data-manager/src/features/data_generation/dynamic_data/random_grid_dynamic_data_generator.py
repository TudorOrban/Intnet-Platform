
import random
from features.grid_graph.models.grid_graph_types import DERType, EdgeType, GeneratorType, GridGraphData, LoadType, StorageUnitType


def generate_random_dynamic_data(graph_data: GridGraphData) -> GridGraphData:
    """Generates random dynamic electric data for the electric grid components."""

    graph_data = generate_random_load_dynamic_data(graph_data)
    graph_data = generate_random_generator_dynamic_data(graph_data)
    graph_data = generate_random_der_dynamic_data(graph_data)
    graph_data = generate_random_storage_unit_dynamic_data(graph_data)
    graph_data = generate_random_bus_dynamic_data(graph_data)
    graph_data = generate_random_edge_dynamic_data(graph_data)

    return graph_data

# Load
LOAD_TYPE_FLUCTUATION = {
    LoadType.RESIDENTIAL: {"p_min_ratio": 0.0, "p_max_ratio": 1.0, "q_min_ratio": 0.0, "q_max_ratio": 1.0},
    LoadType.COMMERCIAL: {"p_min_ratio": 0.9, "p_max_ratio": 0.95, "q_min_ratio": 0.9, "q_max_ratio": 0.95},
    LoadType.INDUSTRIAL: {"p_min_ratio": 0.95, "p_max_ratio": 0.98, "q_min_ratio": 0.95, "q_max_ratio": 0.98},
    LoadType.UNKNOWN: {"p_min_ratio": 0.0, "p_max_ratio": 1.0, "q_min_ratio": 0.0, "q_max_ratio": 1.0},
}

def generate_random_load_dynamic_data(graph_data: GridGraphData) -> GridGraphData:
    for bus in graph_data.buses:
        for load in bus.loads:
            fluctuation = LOAD_TYPE_FLUCTUATION[load.load_type]
            load.state.p_mw = random.uniform(load.min_p_mw * fluctuation["p_min_ratio"], load.max_p_mw * fluctuation["p_max_ratio"])
            load.state.q_mvar = random.uniform(load.min_q_mvar * fluctuation["q_min_ratio"], load.max_q_mvar * fluctuation["q_max_ratio"])
    return graph_data

# Generator
GENERATOR_TYPE_FLUCTUATION = {
    GeneratorType.NUCLEAR: {"p_min_ratio": 0.98, "p_max_ratio": 1.0, "q_min_ratio": 1.0, "q_max_ratio": 1.0},
    GeneratorType.HYDRO: {"p_min_ratio": 0.7, "p_max_ratio": 1.0, "q_min_ratio": 0.8, "q_max_ratio": 1.0},
    GeneratorType.GAS: {"p_min_ratio": 0.7, "p_max_ratio": 1.0, "q_min_ratio": 0.8, "q_max_ratio": 1.0},
    GeneratorType.COAL: {"p_min_ratio": 0.9, "p_max_ratio": 0.98, "q_min_ratio": 0.9, "q_max_ratio": 1.0},
    GeneratorType.OIL: {"p_min_ratio": 0.9, "p_max_ratio": 0.98, "q_min_ratio": 0.9, "q_max_ratio": 1.0},
}

GENERATOR_TYPE_COST = {
    GeneratorType.NUCLEAR: {"min_cost": 5, "max_cost": 10},
    GeneratorType.HYDRO: {"min_cost": 2, "max_cost": 8},
    GeneratorType.GAS: {"min_cost": 15, "max_cost": 30},
    GeneratorType.COAL: {"min_cost": 10, "max_cost": 20},
    GeneratorType.OIL: {"min_cost": 20, "max_cost": 40},
}

def generate_random_generator_dynamic_data(graph_data: GridGraphData) -> GridGraphData:
    for bus in graph_data.buses:
        for gen in bus.generators:
            fluctuation = GENERATOR_TYPE_FLUCTUATION[gen.generator_type]
            gen.state.p_mw = random.uniform(gen.min_p_mw * fluctuation["p_min_ratio"], gen.max_p_mw * fluctuation["p_max_ratio"])
            gen.state.q_mvar = random.uniform(gen.min_q_mvar * fluctuation["q_min_ratio"], gen.max_q_mvar * fluctuation["q_max_ratio"])

            cost_ranges = GENERATOR_TYPE_COST[gen.generator_type]
            gen.state.cp1_eur_per_mw = random.uniform(cost_ranges["min_cost"], cost_ranges["max_cost"])
    return graph_data

# DER
DER_TYPE_FLUCTUATION = {
    DERType.SOLAR: {"p_min_ratio": 0.0, "p_max_ratio": 1.0, "q_min_ratio": -1.0, "q_max_ratio": 1.0},
    DERType.WIND: {"p_min_ratio": 0.0, "p_max_ratio": 1.0, "q_min_ratio": -1.0, "q_max_ratio": 1.0},
}

def generate_random_der_dynamic_data(graph_data: GridGraphData) -> GridGraphData:
    for bus in graph_data.buses:
        for der in bus.ders:
            fluctuation = DER_TYPE_FLUCTUATION[der.der_type]
            der.state.p_mw = random.uniform(der.min_p_mw * fluctuation["p_min_ratio"], der.max_p_mw * fluctuation["p_max_ratio"])
            der.state.q_mvar = random.uniform(der.min_q_mvar * fluctuation["q_min_ratio"], der.max_q_mvar * fluctuation["q_max_ratio"])
    return graph_data

# Storage unit

STORAGE_UNIT_TYPE_FLUCTUATION = {
    StorageUnitType.BATTERY: {
        "p_min_ratio": -1.0,
        "p_max_ratio": 1.0,
        "q_min_ratio": -0.8,
        "q_max_ratio": 0.8,
        "soc_min_ratio": -0.1,
        "soc_max_ratio": 0.1,
    },
    StorageUnitType.PUMPED_HYDRO: {
        "p_min_ratio": -1.0,
        "p_max_ratio": 1.0,
        "q_min_ratio": -0.5,
        "q_max_ratio": 0.5,
        "soc_min_ratio": -0.05,
        "soc_max_ratio": 0.05,
    },
}

def generate_random_storage_unit_dynamic_data(graph_data: GridGraphData) -> GridGraphData:
    for bus in graph_data.buses:
        for storage_unit in bus.storage_units:
            fluctuation = STORAGE_UNIT_TYPE_FLUCTUATION[storage_unit.storage_type]

            storage_unit.state.p_mw = random.uniform(
                storage_unit.min_p_mw * fluctuation["p_min_ratio"],
                storage_unit.max_p_mw * fluctuation["p_max_ratio"],
            )
            storage_unit.state.q_mvar = random.uniform(
                storage_unit.min_q_mvar * fluctuation["q_min_ratio"],
                storage_unit.max_q_mvar * fluctuation["q_max_ratio"],
            )

            soc_change = random.uniform(fluctuation["soc_min_ratio"], fluctuation["soc_max_ratio"])
            storage_unit.state.soc_percent = max(0.0, min(100.0, storage_unit.state.soc_percent + soc_change * 100))

    return graph_data

# Bus
BUS_DYNAMIC_RANGES = {
    "va_deg_variation": (-5, 5),
    "tap_pos_variation": (-0.05, 0.05),
}

def generate_random_bus_dynamic_data(graph_data: GridGraphData) -> GridGraphData:
    for bus in graph_data.buses:
        bus.state.vm_pu = random.uniform(bus.min_vm_pu, bus.max_vm_pu)
        bus.state.va_deg = random.uniform(*BUS_DYNAMIC_RANGES["va_deg_variation"])

        bus.state.p_inj_mw = sum(gen.state.p_mw for gen in bus.generators) - sum(load.state.p_mw for load in bus.loads)
        bus.state.q_inj_mvar = sum(gen.state.q_mvar for gen in bus.generators) - sum(load.state.q_mvar for load in bus.loads)

        bus.state.tap_pos = None # TODO: Alter once transformers are integrated

    return graph_data

# Edge
EDGE_DYNAMIC_RANGES = {
    EdgeType.TRANSMISSION_LINE: {"p_flow_limit": 500, "q_flow_limit": 100, "i_ka_limit": 1.0},
    EdgeType.DISTRIBUTION_LINE: {"p_flow_limit": 50, "q_flow_limit": 10, "i_ka_limit": 0.5},
    EdgeType.TRANSFORMER: {"p_flow_limit": 200, "q_flow_limit": 50, "i_ka_limit": 0.8},
}

def generate_random_edge_dynamic_data(graph_data: GridGraphData) -> GridGraphData:
    for edge in graph_data.edges:
        limits = EDGE_DYNAMIC_RANGES[edge.edge_type]
        edge.state.p_flow_mw = random.uniform(-limits["p_flow_limit"], limits["p_flow_limit"])
        edge.state.q_flow_mvar = random.uniform(-limits["q_flow_limit"], limits["q_flow_limit"])
        edge.state.i_ka = random.uniform(0, limits["i_ka_limit"])
        edge.state.in_service = random.choice([True, False])
    return graph_data