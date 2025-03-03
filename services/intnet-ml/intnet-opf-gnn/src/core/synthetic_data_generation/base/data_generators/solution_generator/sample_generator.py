


from typing import Dict, List
from core.data_types import GridGraphData
from core.synthetic_data_generation.base.data_generators.random_dynamic_data_generator import generate_random_dynamic_data
from core.synthetic_data_generation.base.data_generators.random_grid_topology_generator import generate_random_topology
from core.synthetic_data_generation.base.data_generators.random_static_data_generator import generate_random_static_data
from core.synthetic_data_generation.base.data_generators.solution_generator.sample_types import BusFixedSpecificationSamples, EdgeFixedSpecificationSamples, FixedSpecificationSample, FixedTopologySample, GeneratorFixedSpecificationSamples, LoadFixedSpecificationSamples


def generate_samples(topologies=3, specifications=5, records=10) -> List[FixedTopologySample]:
    """Generates synthetic data samples"""

    samples: List[FixedTopologySample] = []

    for i in range(topologies):
        graph_topology = generate_random_topology(num_buses=20, num_generators=2, num_loads=9, edge_density=0.4)
        sample = generate_fixed_topology_sample(graph_topology=graph_topology, specifications=specifications, records=records)
        samples.append(sample)
    
    return samples

def generate_fixed_topology_sample(graph_topology: GridGraphData, specifications=5, records=10) -> FixedTopologySample:
    fixed_specification_samples: List[FixedSpecificationSample] = []

    for j in range(specifications):
        graph_specification = generate_random_static_data(graph_topology)
        fixed_specification_sample = generate_fixed_specification_sample(graph_specification=graph_specification, records=records)
        fixed_specification_samples.append(fixed_specification_sample)

    return FixedTopologySample(graph_topology, fixed_specification_samples)

def generate_fixed_specification_sample(graph_specification: GridGraphData, records=10) -> FixedSpecificationSample:
    bus_samples: Dict[int, BusFixedSpecificationSamples] = {}
    edge_samples: Dict[int, EdgeFixedSpecificationSamples] = {}
    generator_samples: Dict[int, GeneratorFixedSpecificationSamples] = {}
    load_samples: Dict[int, LoadFixedSpecificationSamples] = {}

    # Initialize dictionaries
    for bus in graph_specification.buses:
        bus_samples[bus.id] = BusFixedSpecificationSamples(bus=bus, bus_states=[])
        for generator in bus.generators:
            generator_samples[generator.id] = GeneratorFixedSpecificationSamples(generator=generator, generator_states=[])
        for load in bus.loads:
            load_samples[load.id] = LoadFixedSpecificationSamples(load=load, load_states=[])

    for edge in graph_specification.edges:
        edge_samples[edge.id] = EdgeFixedSpecificationSamples(edge=edge, edge_states=[])

    # Generate records
    for k in range(records):
        graph_data = generate_random_dynamic_data(graph_specification)

        for bus in graph_data.buses:
            bus_samples[bus.id].bus_states.append(bus.state)

            for generator in bus.generators:
                generator_samples[generator.id].generator_states.append(generator.state)
            
            for load in bus.loads:
                load_samples[load.id].load_states.append(load.state)
        
        for edge in graph_data.edges:
            edge_samples[edge.id].edge_states.append(edge.state)


    return FixedSpecificationSample(bus_samples, edge_samples, generator_samples, load_samples)

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