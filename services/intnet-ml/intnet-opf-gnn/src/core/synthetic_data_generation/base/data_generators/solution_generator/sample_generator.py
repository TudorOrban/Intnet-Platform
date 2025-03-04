import pandapower as pp

from typing import Dict, List
from core.synthetic_data_generation.base.data_generators.solution_generator.network_builder import build_pandapower_network
from core.synthetic_data_generation.common.data_types import GridGraphData
from core.synthetic_data_generation.base.data_generators.random_dynamic_data_generator import generate_random_dynamic_data
from core.synthetic_data_generation.base.data_generators.random_grid_topology_generator import generate_random_topology
from core.synthetic_data_generation.base.data_generators.random_static_data_generator import generate_random_static_data
from core.synthetic_data_generation.base.data_generators.solution_generator.sample_types import BusFixedSpecificationSamples, EdgeFixedSpecificationSamples, FixedSpecificationSample, FixedTopologySample, GeneratorFixedSpecificationSamples, LoadFixedSpecificationSamples


def generate_samples(topologies=3, specifications=5, records=10) -> List[FixedTopologySample]:
    """Generates synthetic data samples"""

    samples: List[FixedTopologySample] = []

    for _ in range(topologies):
        graph_topology = generate_random_topology(num_buses=8, num_generators=2, num_loads=3, edge_density=0.4)
        sample = generate_fixed_topology_sample(graph_topology=graph_topology, specifications=specifications, records=records)
        samples.append(sample)
    
    print("len", len(samples))

    return samples

def generate_fixed_topology_sample(graph_topology: GridGraphData, specifications=5, records=10) -> FixedTopologySample:
    fixed_specification_samples: List[FixedSpecificationSample] = []

    for _ in range(specifications):
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
    for _ in range(records):
        graph_data = generate_random_dynamic_data(graph_specification)

        graph_data = generate_opf_sample(graph_data)

        for bus in graph_data.buses:
            bus_samples[bus.id].bus_states.append(bus.state)

            for generator in bus.generators:
                generator_samples[generator.id].generator_states.append(generator.state)
            
            for load in bus.loads:
                load_samples[load.id].load_states.append(load.state)
        
        for edge in graph_data.edges:
            edge_samples[edge.id].edge_states.append(edge.state)

    return FixedSpecificationSample(bus_samples, edge_samples, generator_samples, load_samples)

def generate_opf_sample(graph_data: GridGraphData) -> GridGraphData:
    """Uses pandapower's OPP to determine generator states"""
    net = build_pandapower_network(graph_data)

    has_converged = False

    try:
        pp.runopp(net)
        has_converged = True
    except Exception as e:
        print(f"OPP didnt converge: {e}")
    if not has_converged:
        return graph_data
    
    print("net gen", net.gen)
    print("has converged", has_converged)
    
    for bus in graph_data.buses:
        for generator in bus.generators:
            generator_index = net.gen.index[net.gen["name"] == str(generator.id)].tolist()[0]
            
            generator_p_mw = net.res_gen.p_mw.values[generator_index]
            print("Generator P MW", generator_p_mw)

            generator.state.p_mw = generator_p_mw

    return graph_data