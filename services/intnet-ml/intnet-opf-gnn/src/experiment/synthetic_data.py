from dataclasses import dataclass
from typing import Any, Dict, List, Tuple
import pandapower as pp
import networkx as nx
import numpy as np

@dataclass
class GridSample:
    load_p_mw: list[float]
    gen_p_mw: float
    adj_matrix: np.ndarray
    node_features: np.ndarray
    edge_features: np.ndarray


def generate_synthetic_data(num_samples=10) -> List[GridSample]:
    """Generates data points for GNN training."""
    
    data: List[GridSample] = []

    for _ in range(num_samples):
        p_mw_load_1 = np.random.uniform(2, 30)
        p_mw_load_2 = np.random.uniform(2, 30)
        p_mw_load_3 = np.random.uniform(2, 30)

        net = generate_random_network(p_mw_load_1, p_mw_load_2, p_mw_load_3)

        pp.runopp(net)

        load_p_mw = [p_mw_load_1, p_mw_load_2, p_mw_load_3]
        gen_p_mw = net.res_gen.p_mw.values[0]

        # Extract adjacency matrix and node features
        graph = nx.Graph()
        for _, row in net.line.iterrows():
            graph.add_edge(row["from_bus"], row["to_bus"])
        
        adj_matrix = nx.adjacency_matrix(graph).todense()
        adj_matrix = np.array(adj_matrix)

        # Extract node features
        node_features = []
        for bus_idx in net.bus.index:
            bus_type = 1.0 if net.bus.at[bus_idx, "type"] == "b" else 0.0
            load_present = 1.0 if bus_idx in net.load.bus.values else 0.0
            gen_present = 1.0 if bus_idx in net.gen.bus.values else 0.0
            vm_pu = net.res_bus.at[bus_idx, "vm_pu"]

            load_power = 0.0
            load_q_mvar = 0.0
            if load_present == 1.0:
                load_index = net.load.bus.values.tolist().index(bus_idx)
                load_power = load_p_mw[load_index]
                load_q_mvar = net.load.at[load_index, "q_mvar"]

            gen_min_p = 0.0
            gen_max_p = 0.0
            gen_min_q = 0.0
            gen_max_q = 0.0
            if gen_present == 1.0:
                gen_index = net.gen.bus.values.tolist().index(bus_idx)
                gen_min_p = net.gen.at[net.gen.bus.values[gen_index], "min_p_mw"]
                gen_max_p = net.gen.at[net.gen.bus.values[gen_index], "max_p_mw"]
                gen_min_q = net.gen.at[net.gen.bus.values[gen_index], "min_q_mvar"]
                gen_max_q = net.gen.at[net.gen.bus.values[gen_index], "max_q_mvar"]

            node_features.append([bus_type, load_present, gen_present, vm_pu, load_power, load_q_mvar, gen_min_p, gen_max_p, gen_min_q, gen_max_q])

        node_features = np.array(node_features)

        # Extract edge features
        edge_features = []
        for _, row in net.line.iterrows():
            r_ohm_per_km = row["r_ohm_per_km"]
            x_ohm_per_km = row["x_ohm_per_km"]
            edge_features.append([r_ohm_per_km, x_ohm_per_km])
        edge_features = np.array(edge_features)

        # Append sample
        sample = GridSample(load_p_mw, gen_p_mw, adj_matrix, node_features, edge_features)
        data.append(sample)

    return data

def generate_random_network(p_mw_load_1: float, p_mw_load_2: float, p_mw_load_3: float):
    """Generates a random pandapower network that converges."""

    net = pp.create_empty_network()

    # Buses
    bus1 = pp.create_bus(net, vn_kv=20., type="b", min_vm_pu=0.9, max_vm_pu=1.1)
    bus2 = pp.create_bus(net, vn_kv=20., type="b", min_vm_pu=0.9, max_vm_pu=1.1)
    bus3 = pp.create_bus(net, vn_kv=20., type="b", min_vm_pu=0.9, max_vm_pu=1.1)
    bus4 = pp.create_bus(net, vn_kv=20., type="b", min_vm_pu=0.9, max_vm_pu=1.1)

    # Lines
    pp.create_line(net, from_bus=bus1, to_bus=bus2, length_km=5., std_type="NAYY 4x150 SE")
    pp.create_line(net, from_bus=bus1, to_bus=bus3, length_km=10., std_type="NAYY 4x150 SE")
    pp.create_line(net, from_bus=bus1, to_bus=bus4, length_km=8., std_type="NAYY 4x150 SE")

    # Generators
    # (Slack)
    pp.create_gen(net, bus=bus1, p_mw=50., min_p_mw=0., max_p_mw=150., q_mvar=0., min_q_mvar=0., max_q_mvar=150., slack=True)
    pp.create_poly_cost(net, element=0, et="gen", cp1_eur_per_mw=10.) 
    
    # Loads
    pp.create_load(net, bus=bus2, p_mw=p_mw_load_1, q_mvar=2.)
    pp.create_load(net, bus=bus3, p_mw=p_mw_load_2, q_mvar=2.)
    pp.create_load(net, bus=bus4, p_mw=p_mw_load_3, q_mvar=3.)

    return net






# net = generate_random_network()

# # pp.runpp(net)
# pp.runopp(net)

# print(net.res_bus)
# # print(net.res_line)
# print(net.res_gen)

# max_i_ka = net.line.max_i_ka.at[0]
# print(f"Max i_ka: {max_i_ka}")

# loading_percent = (net.res_line.i_ka.at[0] / max_i_ka) * 100
# print(f"Loading percentage: {loading_percent}%")
#     # pp.create_ext_grid(net, bus=bus1)
    