import dataclasses
from typing import Dict

from features.dynamic_data_record.models.record_types import RecordData
from features.grid_graph.models.grid_graph_types import BusState, DERState, EdgeState, GeneratorState, GridGraphData, LoadState, StorageUnitState


def extract_dynamic_data_record(graph_data: GridGraphData) -> RecordData:
    """Extracts a record of dynamic data from a grid graph"""

    bus_data: Dict[int, BusState] = {} 
    edge_data: Dict[int, EdgeState] = {}
    generator_data: Dict[int, GeneratorState] = {}
    load_data: Dict[int, LoadState] = {}
    der_data: Dict[int, DERState] = {}
    storage_unit_data: Dict[int, StorageUnitState] = {}

    for bus in graph_data.buses:
        bus_data[bus.id] = bus.state

        for generator in bus.generators:
            generator_data[generator.id] = generator.state
        for load in bus.loads:
            load_data[load.id] = load.state
        for der in bus.ders:
            der_data[der.id] = der.state
        for storage_unit in bus.storage_units:
            storage_unit_data[storage_unit.id] = storage_unit.state

    for edge in graph_data.edges:
        edge_data[edge.id] = edge.state

    return RecordData(bus_data=bus_data, edge_data=edge_data, generator_data=generator_data, load_data=load_data, der_data=der_data, storage_unit_data=storage_unit_data)


def update_graph_with_record(base_graph: GridGraphData, record: RecordData) -> GridGraphData:
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
        updated_ders = []
        for der in updated_bus.ders:
            updated_der = dataclasses.replace(der, state=record.der_data[der.id])
            updated_ders.append(updated_der)
        updated_storage_units = []
        for storage_unit in updated_bus.storage_units:
            updated_storage_unit = dataclasses.replace(storage_unit, state=record.storage_unit_data[storage_unit.id])
            updated_storage_units.append(updated_storage_unit)
        updated_bus = dataclasses.replace(updated_bus, generators=updated_generators, loads=updated_loads, ders=updated_ders, storage_units=updated_storage_units)
        updated_buses.append(updated_bus)

    updated_edges = []
    for edge in base_graph.edges:
        updated_edge = dataclasses.replace(edge, state=record.edge_data[edge.id])
        updated_edges.append(updated_edge)

    return GridGraphData(buses=updated_buses, edges=updated_edges)