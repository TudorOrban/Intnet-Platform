import random
import networkx as nx

from typing import List
from core.common.data_types import Bus, Edge, GridGraphData
from core.data_generators.random_data_generator.random_grid_topology_generator import generate_random_bus, generate_random_edge, generate_random_generator


def generate_realistic_grid_topology(num_buses=10, num_generators=3, num_loads=4, edge_density=0.3) -> GridGraphData:
    """Generates a realistic grid topology incrementally, starting from generators"""

    buses: List[Bus] = []
    edges: List[Edge] = []
    graph = nx.Graph()

    # 1. Initialize Generator Buses
    generator_buses = random.sample(range(num_buses), num_generators)
    for bus_id in generator_buses:
        bus = generate_random_bus(id=bus_id)
        buses.append(bus)
        graph.add_node(bus_id)
        bus.generators.append(generate_random_generator(id=len(bus.generators), bus_id=bus_id, slack=(bus_id == generator_buses[0])))

    # 2. Incremental Expansion
    current_buses = generator_buses.copy()
    remaining_buses = list(set(range(num_buses))) - set(generator_buses)

    while len(remaining_buses) > 0:
        src_bus_id = random.choice(current_buses)
        dest_bus_id = random.choice(remaining_buses)

        new_bus = generate_random_bus(id=dest_bus_id)
        buses.append(new_bus)

        new_edge = generate_random_edge(id=len(edges), u=src_bus_id, v=dest_bus_id)
        edges.append(new_edge)

        graph.add_node(dest_bus_id)
        graph.add_edge(src_bus_id, dest_bus_id)

        