
from typing import Dict, List
from core.data_types import Bus, Edge, EdgeType, GridGraphData
from pandapower import pandapowerNet
import pandapower as pp

def build_pandapower_network(graph_data: GridGraphData) -> pandapowerNet:
    """Builds a pandapower network out of grid graph data for OPF solving"""

    net = pp.create_empty_network()

    node_id_to_index_dict = create_buses(net=net, buses=graph_data.buses)

    create_edges(net=net, edges=graph_data.edges, node_id_to_index_dict=node_id_to_index_dict)

    return net


def create_buses(net: pandapowerNet, buses: List[Bus]) -> Dict[int, int]:
    id_to_index_dict: Dict[int, int] = {}

    for bus in buses:
        bus_index = pp.create_bus(net, vn_kv=bus.vn_kv, type="b", min_vm_pu=bus.min_vm_pu, max_vm_pu=bus.max_vm_pu)
        id_to_index_dict[bus.id] = bus_index

        create_generators(net, bus, bus_index)
        create_loads(net, bus, bus_index)

    return id_to_index_dict

def create_generators(net: pandapowerNet, bus: Bus, bus_index: int):
    for generator in bus.generators:
        generator_index = pp.create_gen(
            net, bus=bus_index, 
            min_p_mw=generator.min_p_mw, max_p_mw=generator.max_p_mw, min_q_mvar=generator.min_q_mvar, max_q_mvar=generator.max_q_mvar,
            p_mw=generator.state.p_mw, q_mvar=generator.state.q_mvar,
            slack=generator.slack
        )
        pp.create_poly_cost(net, element=generator_index, et="gen", cp1_eur_per_mw=generator.state.cp1_eur_per_mw)

def create_loads(net: pandapowerNet, bus: Bus, bus_index: int):
    for load in bus.loads:
        pp.create_load(
            net, bus=bus_index,
            min_p_mw=load.min_p_mw, max_p_mw=load.max_p_mw, min_q_mvar=load.min_q_mvar, max_q_mvar=load.max_q_mvar,
            p_mw=load.state.p_mw, q_mvar=load.state.q_mvar,
        )

def create_edges(net: pandapowerNet, edges: List[Edge], node_id_to_index_dict: Dict[int, int]):
    for edge in edges:
        if edge.edge_type == EdgeType.TRANSFORMER: 
            # print("Adding transformer")
            # pp.create_transformer(
            #     net,
            #     hv_bus=node_id_to_index_dict[edge.src_bus_id],
            #     lv_bus=node_id_to_index_dict[edge.dest_bus_id],
            #     std_type="160 MVA 380/110 kV",
            # )


            continue

        line_index = pp.create_line(
            net,
            from_bus=node_id_to_index_dict[edge.src_bus_id],
            to_bus=node_id_to_index_dict[edge.dest_bus_id],
            length_km=edge.length_km,
            std_type="NAYY 4x150 SE"
        )
        net.line.at[line_index, "r_ohm_per_km"] = edge.r_ohm_per_km
        net.line.at[line_index, "x_ohm_per_km"] = edge.x_ohm_per_km
        net.line.at[line_index, "max_i_ka"] = 1000