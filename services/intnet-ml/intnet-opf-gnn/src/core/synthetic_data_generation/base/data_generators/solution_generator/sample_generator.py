


from typing import List
from core.data_types import GridGraphData
from core.synthetic_data_generation.base.data_generators.random_dynamic_data_generator import generate_random_dynamic_data
from core.synthetic_data_generation.base.data_generators.random_grid_topology_generator import generate_random_topology
from core.synthetic_data_generation.base.data_generators.random_static_data_generator import generate_random_static_data
from core.synthetic_data_generation.base.data_generators.solution_generator.sample_types import FixedTopologySample


def generate_samples(topologies=3, specifications=5, records=10):
    """Generates synthetic data samples"""

    for i in range(topologies):
        graph_data = generate_random_topology(num_buses=20, num_generators=2, num_loads=9, edge_density=0.4)

        for j in range(specifications):
            graph_data = generate_random_static_data(graph_data)

            for k in range(records):
                graph_data = generate_random_dynamic_data(graph_data)

                

def rebuild_graph_data(samples: List[FixedTopologySample], topology_index: int, specification_index: int, record_index: int) -> GridGraphData:
    fixed_topology_sample = samples[topology_index]
    graph_topology = fixed_topology_sample.graph_topology
    fixed_specification_sample = fixed_topology_sample.fixed_specification_samples[specification_index]
        
    for i, bus in enumerate(graph_topology.buses):
        bus_specification_samples = fixed_specification_sample.bus_samples[bus.id]
        bus_state_sample = bus_specification_samples.bus_states[record_index]

        full_buss = bus_specification_samples.bus
        full_buss.state = bus_state_sample
        graph_topology.buses[i] = full_buss

        # for j, generator in enumerate(bus.generators):
            

        