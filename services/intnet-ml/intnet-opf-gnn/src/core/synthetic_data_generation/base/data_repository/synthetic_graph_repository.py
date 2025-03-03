import json
from typing import List
from pathlib import Path

from core.data_types import GridGraph, Bus, Edge, Generator, Load, BusState, EdgeState, GeneratorState, LoadState, BusType

class SyntheticGraphRepository:
    def __init__(self, file_path: str = "synthetic_data/base_graph_data.json"):
        self.file_path = Path(file_path)
        self.file_path.parent.mkdir(parents=True, exist_ok=True) #make sure the parent directory exists

    def read_graphs(self) -> List[GridGraph]:
        if not self.file_path.exists():
            return []

        with open(self.file_path, "r") as f:
            data = json.load(f)

        graphs = []
        for graph_data in data:
            buses = [self._deserialize_bus(bus_data) for bus_data in graph_data["buses"]]
            edges = [self._deserialize_edge(edge_data) for edge_data in graph_data["edges"]]
            graphs.append(GridGraph(buses=buses, edges=edges))

        return graphs

    def add_graph(self, graph: GridGraph):
        graphs = self.read_graphs()
        graphs.append(graph)

        data = [self._serialize_graph(g) for g in graphs]

        with open(self.file_path, "w") as f:
            json.dump(data, f, indent=4)

    def _deserialize_bus(self, bus_data: dict) -> Bus:
        return Bus(
            id=bus_data["id"],
            bus_type=BusType(bus_data["bus_type"]),
            latitude=bus_data["latitude"],
            longitude=bus_data["longitude"],
            min_vm_pu=bus_data["min_vm_pu"],
            max_vm_pu=bus_data["max_vm_pu"],
            vn_kv=bus_data["vn_kv"],
            state=BusState(
                vm_pu=bus_data["state"]["vm_pu"],
                va_deg=bus_data["state"]["va_deg"],
                p_inj_mw=bus_data["state"]["p_inj_mw"],
                q_inj_mvar=bus_data["state"]["q_inj_mvar"],
                tap_pos=bus_data["state"]["tap_pos"]
            ),
            generators=[self._deserialize_generator(gen_data) for gen_data in bus_data["generators"]],
            loads=[self._deserialize_load(load_data) for load_data in bus_data["loads"]]
        )

    def _deserialize_edge(self, edge_data: dict) -> Edge:
        return Edge(
            id=edge_data["id"],
            src_bus_id=edge_data["src_bus_id"],
            dest_bus_id=edge_data["dest_bus_id"],
            edge_type=edge_data["edge_type"],
            length_km=edge_data["length_km"],
            r_ohm_per_km=edge_data["r_ohm_per_km"],
            x_ohm_per_km=edge_data["x_ohm_per_km"],
            state=EdgeState(
                p_flow_mw=edge_data["state"]["p_flow_mw"],
                q_flow_mvar=edge_data["state"]["q_flow_mvar"],
                i_ka=edge_data["state"]["i_ka"],
                in_service=edge_data["state"]["in_service"]
            )
        )

    def _deserialize_generator(self, gen_data: dict) -> Generator:
        return Generator(
            id=gen_data["id"],
            bus_id=gen_data["bus_id"],
            min_p_mw=gen_data["min_p_mw"],
            max_p_mw=gen_data["max_p_mw"],
            min_q_mvar=gen_data["min_q_mvar"],
            max_q_mvar=gen_data["max_q_mvar"],
            slack=gen_data["slack"],
            state=GeneratorState(
                p_mw=gen_data["state"]["p_mw"],
                q_mvar=gen_data["state"]["q_mvar"]
            )
        )

    def _deserialize_load(self, load_data: dict) -> Load:
        return Load(
            id=load_data["id"],
            bus_id=load_data["bus_id"],
            min_p_mw=load_data["min_p_mw"],
            max_p_mw=load_data["max_p_mw"],
            min_q_mvar=load_data["min_q_mvar"],
            max_q_mvar=load_data["max_q_mvar"],
            state=LoadState(
                p_mw=load_data["state"]["p_mw"],
                q_mvar=load_data["state"]["q_mvar"]
            )
        )

    def _serialize_graph(self, graph: GridGraph) -> dict:
        return {
            "buses": [self._serialize_bus(bus) for bus in graph.buses],
            "edges": [self._serialize_edge(edge) for edge in graph.edges]
        }

    def _serialize_bus(self, bus: Bus) -> dict:
        return {
            "id": bus.id,
            "bus_type": bus.bus_type.value,
            "latitude": bus.latitude,
            "longitude": bus.longitude,
            "min_vm_pu": bus.min_vm_pu,
            "max_vm_pu": bus.max_vm_pu,
            "vn_kv": bus.vn_kv,
            "state": {
                "vm_pu": bus.state.vm_pu,
                "va_deg": bus.state.va_deg,
                "p_inj_mw": bus.state.p_inj_mw,
                "q_inj_mvar": bus.state.q_inj_mvar,
                "tap_pos": bus.state.tap_pos
            },
            "generators": [self._serialize_generator(gen) for gen in bus.generators],
            "loads": [self._serialize_load(load) for load in bus.loads]
        }

    def _serialize_edge(self, edge: Edge) -> dict:
        return {
            "id": edge.id,
            "src_bus_id": edge.src_bus_id,
            "dest_bus_id": edge.dest_bus_id,
            "edge_type": edge.edge_type.value,
            "length_km": edge.length_km,
            "r_ohm_per_km": edge.r_ohm_per_km,
            "x_ohm_per_km": edge.x_ohm_per_km,
            "state": {
                "p_flow_mw": edge.state.p_flow_mw,
                "q_flow_mvar": edge.state.q_flow_mvar,
                "i_ka": edge.state.i_ka,
                "in_service": edge.state.in_service
            }
        }

    def _serialize_generator(self, gen: Generator) -> dict:
        return {
            "id": gen.id,
            "bus_id": gen.bus_id,
            "min_p_mw": gen.min_p_mw,
            "max_p_mw": gen.max_p_mw,
            "min_q_mvar": gen.min_q_mvar,
            "max_q_mvar": gen.max_q_mvar,
            "slack": gen.slack,
            "state": {
                "p_mw": gen.state.p_mw,
                "q_mvar": gen.state.q_mvar
            }
        }

    def _serialize_load(self, load: Load) -> dict:
        return {
            "id": load.id,
            "bus_id": load.bus_id,
            "min_p_mw": load.min_p_mw,
            "max_p_mw": load.max_p_mw,
            "min_q_mvar": load.min_q_mvar,
            "max_q_mvar": load.max_q_mvar,
            "state": {
                "p_mw": load.state.p_mw,
                "q_mvar": load.state.q_mvar
            }
        }
    