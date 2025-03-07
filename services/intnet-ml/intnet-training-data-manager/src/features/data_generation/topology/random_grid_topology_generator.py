import random
import networkx as nx

from typing import List
from features.data_generation.topology.initial_component_generator import generate_bus, generate_der, generate_edge, generate_generator, generate_load, generate_storage_unit
from features.grid_graph.models.grid_graph_types import Bus, Edge, GridGraphData


def generate_random_grid_topology(num_buses=10, num_generators=3, num_loads=4, der_density=0.2, storage_unit_density=0.1, edge_density=0.1) -> GridGraphData:
    """Generates a random grid topology incrementally, starting from generators"""

    buses: List[Bus] = []
    edges: List[Edge] = []
    graph = nx.Graph()
    load_count = 0
    der_count = 0
    storage_unit_count = 0

    # 1. Initialize Generator Buses
    generator_buses = random.sample(range(num_buses), num_generators)
    for bus_id in generator_buses:
        bus = generate_bus(id=bus_id)
        buses.append(bus)
        graph.add_node(bus_id)
        bus.generators.append(generate_generator(id=len(bus.generators), bus_id=bus_id, slack=(bus_id == generator_buses[0])))

    # 2. Generate disconnected trees from generators, adding loads and ders occasionally
    current_buses = generator_buses.copy()
    remaining_buses = list(set(range(num_buses)) - set(generator_buses))

    while len(remaining_buses) > 0:
        src_bus_id = random.choice(current_buses)
        dest_bus_id = random.choice(remaining_buses)

        new_bus = generate_bus(id=dest_bus_id)
        buses.append(new_bus)

        new_edge = generate_edge(id=len(edges), u=src_bus_id, v=dest_bus_id)
        edges.append(new_edge)

        graph.add_node(dest_bus_id)
        graph.add_edge(src_bus_id, dest_bus_id)

        if random.random() < 0.5 and load_count < num_loads:
            new_load = generate_load(id=load_count, bus_id=dest_bus_id)
            new_bus.loads.append(new_load)
            load_count += 1

        if random.random() < der_density:
            new_der = generate_der(id=der_count, bus_id=dest_bus_id)
            new_bus.ders.append(new_der)
            der_count += 1

        if random.random() < storage_unit_density:
            new_storage_unit = generate_storage_unit(id=storage_unit_count, bus_id=dest_bus_id)
            new_bus.storage_units.append(new_storage_unit)
            storage_unit_count += 1

        current_buses.append(dest_bus_id)
        remaining_buses.remove(dest_bus_id)
    
    # Connect trees
    for _ in range(int(len(edges) * edge_density)):
        bus1 = random.choice(buses)
        bus2 = random.choice(buses)
        if bus1.id != bus2.id and (bus1.id, bus2.id) not in graph.edges() and (bus2.id, bus1.id) not in graph.edges():
            new_edge = generate_edge(id=len(edges), u=bus1.id, v=bus2.id)
            edges.append(new_edge)
            graph.add_edge(bus1.id, bus2.id)

    # Distribute remaining loads
    loadless_buses = list(filter(lambda x: len(x.loads) == 0, buses))
    while load_count < num_loads and len(loadless_buses) > 0:
        bus = random.choice(loadless_buses)
        new_load = generate_load(id=len(bus.loads), bus_id=bus.id)
        bus.loads.append(new_load)
        load_count += 1

    return GridGraphData(buses, edges)