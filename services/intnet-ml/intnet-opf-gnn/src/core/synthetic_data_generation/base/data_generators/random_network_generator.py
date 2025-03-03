

import random
from typing import Generator, List
from core.data_types import Bus, BusState, BusType, Edge, GridGraph, Load
import networkx as nx

def generate_random_topology(num_buses: int, num_generators: int, num_loads: int, edge_density: float=0.5) -> GridGraph:
    """Generates a random, solvable power network topology."""

    buses: List[Bus] = []
    edges: List[Edge] = []

    # Generate buses
    for i in range(num_buses):
        buses.append(
            generate_random_bus(id=i)
        )

    # Generate edges (minimum spanning edges + extra)
    graph = nx.complete_graph(num_buses)
    mst = nx.minimum_spanning_edges(graph, data=False)
    mst_edges = list(mst)

    for u, v in mst_edges:
        edges.append(
            generate_random_edge(id=len(edges), u=u, v=v)
        )
    
    num_extra_edges = int(num_buses * edge_density)
    for _ in range(num_extra_edges):
        u, v= random.sample(range(num_buses), 2)
        if (u, v) in mst_edges or (v, u) in mst_edges:
            continue

        edges.append(
            generate_random_edge(id=len(edges), u=u, v=v)
        )

    # Generate generators
    generator_buses = random.sample(range(num_buses), num_generators)
    for i, bus_id in enumerate(generator_buses):
        buses[bus_id].generators.append(
            generate_random_generator(id=i, bus_id=bus_id, slack=(i == 0))
        )

    # Generate loads
    load_buses = random.sample(range(num_buses), num_loads)
    for i, bus_id in enumerate(load_buses):
        buses[bus_id].loads.append(
            generate_random_load(id=i, bus_id=bus_id)
        )

    return GridGraph(buses=buses, edges=edges)

def generate_random_bus(id: int) -> Bus:
    return Bus(
        id=id, bus_type=BusType.PQ, latitude=random.uniform(0, 100), longitude=random.uniform(0, 100), 
        min_vm_pu=0.9, max_vm_pu=1.1, vn_kv=20.0, 
        generators=[], loads=[], state=BusState(vm_pu=0, va_deg=0, p_inj_mw=0, q_inj_mvar=0, tap_pos=None)
    )

def generate_random_edge(id: int, u: int, v: int) -> Edge:
    return Edge(
        id=id, src_bus_id=u, dest_bus_id=v, edge_type=random.choice(["TRANSMISSION_LINE", "DISTRIBUTION_LINE", "TRANSFORMER"]),
        length_km=random.uniform(1, 50), r_ohm_per_km=0.01, x_ohm_per_km=0.1
    )

def generate_random_generator(id: int, bus_id: int, slack: bool) -> Generator:
    return Generator(
        id=id, bus_id=bus_id, 
        min_p_mw=0, max_p_mw=100, min_q_mvar=-50, max_q_mvar=50, slack=slack, p_mw=0, q_mvar=0
    )

def generate_random_load(id: int, bus_id: int) -> Load:
    return Load(
        id=id, bus_id=bus_id, 
        p_mw=0, q_mvar=0, max_p_mw=100, max_q_mvar=50
    )