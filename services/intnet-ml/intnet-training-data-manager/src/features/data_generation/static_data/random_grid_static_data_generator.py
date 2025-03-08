
import random
from features.grid_graph.models.grid_graph_types import DERType, EdgeType, GeneratorType, GridGraphData, LoadType, StorageUnitType

def generate_random_static_data(graph_topology: GridGraphData) -> GridGraphData:
    """Generates random static electric data for the electric grid components"""
    
    graph_data = generate_random_load_data(graph_topology)
    graph_data = generate_random_generator_data(graph_data)
    graph_data = generate_random_der_data(graph_data)
    graph_data = generate_random_storage_unit_data(graph_data)
    graph_data = generate_random_bus_data(graph_data)
    graph_data = generate_random_edge_data(graph_data)

    return graph_data

# Loads
LOAD_TYPE_RANGES = {
    LoadType.RESIDENTIAL: {"min_p_mw": 0.1, "max_p_mw": 5, "min_q_mvar": 0.05, "max_q_mvar": 2},
    LoadType.COMMERCIAL: {"min_p_mw": 2, "max_p_mw": 15, "min_q_mvar": 1, "max_q_mvar": 8},
    LoadType.INDUSTRIAL: {"min_p_mw": 10, "max_p_mw": 50, "min_q_mvar": 5, "max_q_mvar": 20},
    LoadType.UNKNOWN: {"min_p_mw": 0.1, "max_p_mw": 5, "min_q_mvar": 0.05, "max_q_mvar": 2},
}

LOAD_TYPE_PROBABILITIES = {
    LoadType.RESIDENTIAL: 0.7,
    LoadType.COMMERCIAL: 0.2,
    LoadType.INDUSTRIAL: 0.05,
    LoadType.UNKNOWN: 0.05,
}

def generate_random_load_data(graph_data: GridGraphData) -> GridGraphData:
    load_types = list(LOAD_TYPE_PROBABILITIES.keys())
    probabilities = list(LOAD_TYPE_PROBABILITIES.values())

    for bus in graph_data.buses:
        for load in bus.loads:
            load.load_type = random.choices(load_types, probabilities)[0]

            load_ranges = LOAD_TYPE_RANGES[load.load_type]
            load.min_p_mw = 0
            load.max_p_mw = random.uniform(load_ranges["min_p_mw"], load_ranges["max_p_mw"])
            load.min_q_mvar = -random.uniform(load_ranges["min_q_mvar"], load_ranges["max_q_mvar"])
            load.max_q_mvar = random.uniform(load_ranges["min_q_mvar"], load_ranges["max_q_mvar"])
    return graph_data

# Generators
GENERATOR_TYPE_RANGES = {
    GeneratorType.HYDRO: {"p_min_mult": 0.5, "p_max_mult": 2.0, "q_min_mult": -0.4, "q_max_mult": 0.4},
    GeneratorType.NUCLEAR: {"p_min_mult": 0.9, "p_max_mult": 1.1, "q_min_mult": -0.3, "q_max_mult": 0.3},
    GeneratorType.COAL: {"p_min_mult": 0.6, "p_max_mult": 1.4, "q_min_mult": -0.5, "q_max_mult": 0.5},
    GeneratorType.GAS: {"p_min_mult": 0.7, "p_max_mult": 1.3, "q_min_mult": -0.6, "q_max_mult": 0.6},
    GeneratorType.OIL: {"p_min_mult": 0.6, "p_max_mult": 1.2, "q_min_mult": -0.4, "q_max_mult": 0.4},
}

GENERATOR_TYPE_PROBABILITIES = {
    GeneratorType.HYDRO: 0.1,
    GeneratorType.NUCLEAR: 0.05,
    GeneratorType.COAL: 0.2,
    GeneratorType.GAS: 0.5,
    GeneratorType.OIL: 0.15,
}

def generate_random_generator_data(graph_data: GridGraphData) -> GridGraphData:
    available_buses = [bus for bus in graph_data.buses if bus.generators]
    if not available_buses:
        return graph_data

    total_load_max_p_mw = sum(load.max_p_mw for bus in graph_data.buses for load in bus.loads)
    
    safety_margin = 1.1
    variation = 0.2

    generator_types = list(GENERATOR_TYPE_PROBABILITIES.keys())
    probabilities = list(GENERATOR_TYPE_PROBABILITIES.values())

    for bus in available_buses:
        for gen in bus.generators:
            gen_type = random.choices(generator_types, probabilities)[0]
            gen.generator_type = gen_type

            gen_ranges = GENERATOR_TYPE_RANGES[gen_type]

            max_p_mw_per_gen = (total_load_max_p_mw * safety_margin / len(bus.generators))

            gen.min_p_mw = 0
            gen.max_p_mw = random.uniform(max_p_mw_per_gen * gen_ranges["p_min_mult"] * (1 - variation),
                                          max_p_mw_per_gen * gen_ranges["p_max_mult"] * (1 + variation))
            gen.min_q_mvar = gen.max_p_mw * gen_ranges["q_min_mult"]
            gen.max_q_mvar = gen.max_p_mw * gen_ranges["q_max_mult"]

    return graph_data

# DERs
DER_TYPE_RANGES = {
    DERType.SOLAR: {"p_min_mult": 0.5, "p_max_mult": 0.8, "q_min_mult": -0.4, "q_max_mult": 0.4},
    DERType.WIND: {"p_min_mult": 0.7, "p_max_mult": 1.1, "q_min_mult": -0.4, "q_max_mult": 0.4},
}

DER_TYPE_PROBABILITIES = {
    DERType.SOLAR: 0.1,
    DERType.WIND: 0.05,
}

def generate_random_der_data(graph_data: GridGraphData) -> GridGraphData:
    available_buses = [bus for bus in graph_data.buses if bus.ders]
    if not available_buses:
        return graph_data
    
    total_load_max_p_mw = sum(load.max_p_mw for bus in graph_data.buses for load in bus.loads)
    
    safety_margin = 0.04
    variation = 0.02

    der_types = list(DER_TYPE_PROBABILITIES.keys())
    probabilities = list(DER_TYPE_PROBABILITIES.values())

    for bus in available_buses:
        for der in bus.ders:
            der_type = random.choices(der_types, probabilities)[0]
            der.der_type = der_type

            der_ranges = DER_TYPE_RANGES[der_type]

            max_p_mw_per_der = (total_load_max_p_mw * safety_margin / len(bus.ders))

            der.min_p_mw = 0
            der.max_p_mw = random.uniform(max_p_mw_per_der * der_ranges["p_min_mult"] * (1 - variation),
                                          max_p_mw_per_der * der_ranges["p_max_mult"] * (1 + variation))
            der.min_q_mvar = der.min_p_mw * der_ranges["q_min_mult"]
            der.max_q_mvar = der.max_p_mw * der_ranges["q_max_mult"]

    return graph_data

# Storage Units
STORAGE_UNIT_TYPE_RANGES = {
    StorageUnitType.BATTERY: {
        "p_min_mult": 0.2,
        "p_max_mult": 0.8,
        "q_min_mult": -0.3,
        "q_max_mult": 0.3,
        "e_min_mult": 0.1,
        "e_max_mult": 0.9,
    },
    StorageUnitType.PUMPED_HYDRO: {
        "p_min_mult": 0.5,
        "p_max_mult": 1.0,
        "q_min_mult": -0.2,
        "q_max_mult": 0.2,
        "e_min_mult": 0.2,
        "e_max_mult": 0.95,
    },
}

STORAGE_UNIT_TYPE_PROBABILITIES = {
    StorageUnitType.BATTERY: 0.2,
    StorageUnitType.PUMPED_HYDRO: 0.1,
}

def generate_random_storage_unit_data(graph_data: GridGraphData) -> GridGraphData:
    available_buses = [bus for bus in graph_data.buses if bus.storage_units]
    if not available_buses:
        return graph_data

    total_load_max_p_mw = sum(load.max_p_mw for bus in graph_data.buses for load in bus.loads)

    safety_margin = 0.05
    variation = 0.05

    storage_types = list(STORAGE_UNIT_TYPE_PROBABILITIES.keys())
    probabilities = list(STORAGE_UNIT_TYPE_PROBABILITIES.values())

    for bus in available_buses:
        for storage_unit in bus.storage_units:
            storage_type = random.choices(storage_types, probabilities)[0]
            storage_unit.storage_type = storage_type

            storage_ranges = STORAGE_UNIT_TYPE_RANGES[storage_type]

            max_p_mw_per_storage = (total_load_max_p_mw * safety_margin / len(bus.storage_units))

            storage_unit.min_p_mw = -random.uniform(max_p_mw_per_storage * storage_ranges["p_min_mult"] * (1 - variation),
                                                  max_p_mw_per_storage * storage_ranges["p_max_mult"] * (1 + variation))
            storage_unit.max_p_mw = random.uniform(max_p_mw_per_storage * storage_ranges["p_min_mult"] * (1 - variation),
                                                  max_p_mw_per_storage * storage_ranges["p_max_mult"] * (1 + variation))

            storage_unit.min_q_mvar = storage_unit.min_p_mw * storage_ranges["q_min_mult"]
            storage_unit.max_q_mvar = storage_unit.max_p_mw * storage_ranges["q_max_mult"]

            max_e_mwh_per_storage = total_load_max_p_mw * 2

            storage_unit.min_e_mwh = random.uniform(max_e_mwh_per_storage * storage_ranges["e_min_mult"] * (1 - variation),
                                                     max_e_mwh_per_storage * storage_ranges["e_max_mult"] * (1 + variation))

            storage_unit.max_e_mwh = random.uniform(max_e_mwh_per_storage * storage_ranges["e_min_mult"] * (1 - variation),
                                                     max_e_mwh_per_storage * storage_ranges["e_max_mult"] * (1 + variation))

            storage_unit.sn_mva = max(abs(storage_unit.min_p_mw), storage_unit.max_p_mw) * 1.2
            storage_unit.controllable = True

    return graph_data

# Buses
def generate_random_bus_data(graph_data: GridGraphData) -> GridGraphData:
    for bus in graph_data.buses:
        bus.vn_kv = 110
        bus.min_vm_pu = 0.9
        bus.max_vm_pu = 1.1
    return graph_data

# Edges
EDGE_TYPE_RANGES = {
    EdgeType.TRANSMISSION_LINE: {
        "r_ohm_per_km": (0.01, 0.05),
        "x_ohm_per_km": (0.2, 0.4),
        "length_km": (1, 20),
    },
    EdgeType.DISTRIBUTION_LINE: {
        "r_ohm_per_km": (0.2, 0.8),
        "x_ohm_per_km": (0.1, 0.3),
        "length_km": (0.2, 5),
    },
    # EdgeType.TRANSFORMER: {
    #     "r_ohm_per_km": (0.001, 0.005),
    #     "x_ohm_per_km": (0.01, 0.03),
    #     "length_km": 0,
    # },
}

def generate_random_edge_data(graph_data: GridGraphData) -> GridGraphData:
    for edge in graph_data.edges:
        ranges = EDGE_TYPE_RANGES[edge.edge_type]
        edge.r_ohm_per_km = random.uniform(*ranges["r_ohm_per_km"])
        edge.x_ohm_per_km = random.uniform(*ranges["x_ohm_per_km"])
        edge.length_km = random.uniform(*ranges["length_km"]) if edge.edge_type != EdgeType.TRANSFORMER else 0

    return graph_data
