

from typing import Dict, List
from core.common.data_types import BusState, EdgeState, GeneratorState, GridGraphData, LoadState
from core.data_generators.random_data_generator.random_dynamic_data_generator import generate_random_dynamic_data
from finetuning.common.data_types import DynamicDataRecord


def generate_dynamic_data_records(graph_data: GridGraphData, record_count=100) -> List[DynamicDataRecord]:
    """Generates a set of records of random dynamic data for a grid graph with specified topology and static data"""

    records: List[DynamicDataRecord] = []

    for record in range(record_count):
        graph_data = generate_random_dynamic_data(graph_data)

        record = extract_dynamic_data_record(graph_data)
        records.append(record)

    return records


def extract_dynamic_data_record(graph_data: GridGraphData) -> DynamicDataRecord:
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
