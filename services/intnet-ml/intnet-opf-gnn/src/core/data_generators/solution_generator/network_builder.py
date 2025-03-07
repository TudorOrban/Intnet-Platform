
from typing import List
from core.common.data_types import Bus, Edge, EdgeType, GridGraphData
from pandapower import pandapowerNet
import pandapower as pp

def build_pandapower_network(graph_data: GridGraphData) -> pandapowerNet:
    """Builds a pandapower network out of grid graph data for OPF solving"""

    net = pp.create_empty_network()

    create_buses(net=net, buses=graph_data.buses)

    create_edges(net=net, edges=graph_data.edges)

    return net


def create_buses(net: pandapowerNet, buses: List[Bus]):
    for bus in buses:
        pp.create_bus(
            net, name=str(bus.id),
            vn_kv=bus.vn_kv, type="b", min_vm_pu=bus.min_vm_pu, max_vm_pu=bus.max_vm_pu
        )

        create_generators(net, bus)
        create_loads(net, bus)

def create_generators(net: pandapowerNet, bus: Bus):
    bus_index = net.bus.index[net.bus["name"] == str(bus.id)].tolist()[0]

    for generator in bus.generators:
        generator_index = pp.create_gen(
            net, bus=bus_index, name=str(generator.id),
            min_p_mw=generator.min_p_mw, max_p_mw=generator.max_p_mw, min_q_mvar=generator.min_q_mvar, max_q_mvar=generator.max_q_mvar,
            p_mw=generator.state.p_mw, q_mvar=generator.state.q_mvar,
            slack=generator.slack
        )
        pp.create_poly_cost(net, element=generator_index, et="gen", cp1_eur_per_mw=generator.state.cp1_eur_per_mw)

def create_loads(net: pandapowerNet, bus: Bus):
    bus_index = net.bus.index[net.bus["name"] == str(bus.id)].tolist()[0]

    for load in bus.loads:
        pp.create_load(
            net, bus=bus_index,
            min_p_mw=load.min_p_mw, max_p_mw=load.max_p_mw, min_q_mvar=load.min_q_mvar, max_q_mvar=load.max_q_mvar,
            p_mw=load.state.p_mw, q_mvar=load.state.q_mvar,
        )

def create_ders(net: pandapowerNet, bus: Bus):
    bus_index = net.bus.index[net.bus["name"] == str(bus.id)].tolist()[0]

    for der in bus.ders:
        pp.create_gen(
            net, bus=bus_index, name=str(der.id),
            min_p_mw=der.state.p_mw, max_p_mw=der.state.p_mw, min_q_mvar=der.state.q_mvar, max_q_mvar=der.state.q_mvar, # Fixed power
            p_mw=der.state.p_mw, q_mvar=der.state.q_mvar,
            slack=False
        )

def create_edges(net: pandapowerNet, edges: List[Edge]):
    for edge in edges:
        if edge.edge_type == EdgeType.TRANSFORMER: 
            continue

        src_bus_index = net.bus.index[net.bus["name"] == str(edge.src_bus_id)].tolist()[0]
        dest_bus_index = net.bus.index[net.bus["name"] == str(edge.dest_bus_id)].tolist()[0]

        line_index = pp.create_line(
            net,
            from_bus=src_bus_index,
            to_bus=dest_bus_index,
            length_km=edge.length_km,
            std_type="NAYY 4x150 SE"
        )
        net.line.at[line_index, "r_ohm_per_km"] = edge.r_ohm_per_km
        net.line.at[line_index, "x_ohm_per_km"] = edge.x_ohm_per_km
        net.line.at[line_index, "max_i_ka"] = 1000
