
from typing import List

from core.synthetic_data_generation.base.data_generators.sample_manager.sample_types import FixedTopologySample
from core.synthetic_data_generation.common.data_types import GridGraphData


def get_graph_data_flat_samples(samples: List[FixedTopologySample]) -> List[GridGraphData]:
    """Flatten samples by reconstructing each graph data"""
    
    graph_data_records: List[GridGraphData] = []

    for topology_index in range(len(samples)):
        for specification_index in range(len(samples[topology_index].fixed_specification_samples)):
            for record_index in range(len(samples[topology_index].fixed_specification_samples[specification_index].bus_samples)):
                graph_data_record = rebuild_graph_data(samples, topology_index, specification_index, record_index)
                graph_data_records.append(graph_data_record)

    return graph_data_records


def rebuild_graph_data(samples: List[FixedTopologySample], topology_index: int, specification_index: int, record_index: int) -> GridGraphData:
    fixed_topology_sample = samples[topology_index]
    graph_topology = fixed_topology_sample.graph_topology
    fixed_specification_sample = fixed_topology_sample.fixed_specification_samples[specification_index]
        
    for i, bus in enumerate(graph_topology.buses):
        bus_specification_samples = fixed_specification_sample.bus_samples[bus.id]
        print(topology_index, specification_index, record_index)
        bus_state_sample = bus_specification_samples.bus_states[record_index]

        sample_bus = bus_specification_samples.bus
        sample_bus.state = bus_state_sample
        graph_topology.buses[i] = sample_bus

        for j, generator in enumerate(bus.generators):
            generator_specification_samples = fixed_specification_sample.generator_samples[generator.id]
            generator_state_sample = generator_specification_samples.generator_states[record_index]
            
            sample_generator = generator_specification_samples.generator
            sample_generator.state = generator_state_sample
            graph_topology.buses[i].generators[j] = sample_generator

        for j, load in enumerate(bus.loads):
            load_specification_samples = fixed_specification_sample.load_samples[load.id]
            load_state_sample = load_specification_samples.load_states[record_index]
            
            sample_load = load_specification_samples.load
            sample_load.state = load_state_sample
            graph_topology.buses[i].loads[j] = sample_load

    for i, edge in enumerate(graph_topology.edges):
        edge_specification_samples = fixed_specification_sample.edge_samples[edge.id]
        edge_state_sample = edge_specification_samples.edge_states[record_index]

        sample_edge = edge_specification_samples.edge
        sample_edge.state = edge_state_sample
        graph_topology.edges[i] = sample_edge