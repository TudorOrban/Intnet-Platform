import dataclasses
from typing import Dict
from core.common.data_types import BusState, EdgeState, GeneratorState, GridGraphData, LoadState
from finetuning.common.data_types import DynamicDataRecord


def extract_dynamic_data_record(graph_data: GridGraphData) -> DynamicDataRecord:
    """Extracts a record of dynamic data from a grid graph"""

    bus_data: Dict[int, BusState] = {} 
    edge_data: Dict[int, EdgeState] = {}
    generator_data: Dict[int, GeneratorState] = {}
    load_data: Dict[int, LoadState] = {}

    for bus in graph_data.buses:
        bus_data[bus.id] = bus.state

        for generator in bus.generators:
            generator_data[generator.id] = generator.state
        for load in bus.loads:
            load_data[load.id] = load.state

    for edge in graph_data.edges:
        edge_data[edge.id] = edge.state

    return DynamicDataRecord(bus_data, edge_data, generator_data, load_data)


def update_graph_with_record(base_graph: GridGraphData, record: DynamicDataRecord) -> GridGraphData:
    """Updates the base graph with dynamic data from a record."""

    updated_buses = []
    for bus in base_graph.buses:
        updated_bus = dataclasses.replace(bus, state=record.bus_data[bus.id])
        updated_generators = []
        for generator in updated_bus.generators:
            updated_generator = dataclasses.replace(generator, state=record.generator_data[generator.id])
            updated_generators.append(updated_generator)
        updated_loads = []
        for load in updated_bus.loads:
            updated_load = dataclasses.replace(load, state=record.load_data[load.id])
            updated_loads.append(updated_load)
        updated_bus = dataclasses.replace(updated_bus, generators=updated_generators, loads = updated_loads)
        updated_buses.append(updated_bus)

    updated_edges = []
    for edge in base_graph.edges:
        updated_edge = dataclasses.replace(edge, state=record.edge_data[edge.id])
        updated_edges.append(updated_edge)

    return GridGraphData(buses=updated_buses, edges=updated_edges)