from features.grid_graph.models.grid_graph_types import BusType, DERType, Edge, EdgeState, EdgeType, GeneratorType, GridGraph, GridGraphData, Bus, BusState, Generator, GeneratorState, Load, LoadState, DER, DERState, LoadType, StorageUnit, StorageUnitState, StorageUnitType


class GridGraphJsonMapper:

    # Graph Data
    @staticmethod
    def deserialize_grid_graph(grid_graph: dict) -> GridGraph:
        return GridGraph(
            id=grid_graph["id"],
            created_at=grid_graph["created_at"],
            graph_data=GridGraphJsonMapper.deserialize_graph_data(grid_graph["graph_data"])
        )

    @staticmethod
    def deserialize_graph_data(graph_data: dict) -> GridGraphData:
        return GridGraphData(
            buses=[GridGraphJsonMapper.deserialize_bus(bus_data) for bus_data in graph_data["buses"]],
            edges=[GridGraphJsonMapper.deserialize_edge(edge_data) for edge_data in graph_data["edges"]]
        )

    @staticmethod
    def deserialize_bus(bus_data: dict) -> Bus:
        return Bus(
            id=bus_data["id"],
            bus_type=BusType(bus_data["bus_type"]),
            latitude=bus_data["latitude"],
            longitude=bus_data["longitude"],
            min_vm_pu=bus_data["min_vm_pu"],
            max_vm_pu=bus_data["max_vm_pu"],
            vn_kv=bus_data["vn_kv"],
            state=GridGraphJsonMapper.deserialize_bus_state(bus_data["state"]),
            generators=[GridGraphJsonMapper.deserialize_generator(gen_data) for gen_data in bus_data["generators"]],
            loads=[GridGraphJsonMapper.deserialize_load(load_data) for load_data in bus_data["loads"]],
            ders=[GridGraphJsonMapper.deserialize_der(der_data) for der_data in bus_data["ders"]],
            storage_units=[GridGraphJsonMapper.deserialize_storage_unit(storage_unit_data) for storage_unit_data in bus_data["storage_units"]],
        )

    @staticmethod
    def deserialize_bus_state(state_data: dict) -> BusState:
        return BusState(
            bus_id=state_data["bus_id"],
            vm_pu=state_data["vm_pu"],
            va_deg=state_data["va_deg"],
            p_inj_mw=state_data["p_inj_mw"],
            q_inj_mvar=state_data["q_inj_mvar"],
            tap_pos=state_data["tap_pos"]
        )

    @staticmethod
    def deserialize_edge(edge_data: dict) -> Edge:
        return Edge(
            id=edge_data["id"],
            src_bus_id=edge_data["src_bus_id"],
            dest_bus_id=edge_data["dest_bus_id"],
            edge_type=EdgeType(edge_data["edge_type"]),
            length_km=edge_data["length_km"],
            r_ohm_per_km=edge_data["r_ohm_per_km"],
            x_ohm_per_km=edge_data["x_ohm_per_km"],
            state=GridGraphJsonMapper.deserialize_edge_state(edge_data["state"])
        )
    
    @staticmethod
    def deserialize_edge_state(state_data: dict) -> EdgeState:
        return EdgeState(
            edge_id=state_data["edge_id"],
            p_flow_mw=state_data["p_flow_mw"],
            q_flow_mvar=state_data["q_flow_mvar"],
            i_ka=state_data["i_ka"],
            in_service=state_data["in_service"]
        )

    @staticmethod
    def deserialize_generator(gen_data: dict) -> Generator:
        return Generator(
            id=gen_data["id"],
            bus_id=gen_data["bus_id"],
            generator_type=GeneratorType(gen_data["generator_type"]),
            min_p_mw=gen_data["min_p_mw"],
            max_p_mw=gen_data["max_p_mw"],
            min_q_mvar=gen_data["min_q_mvar"],
            max_q_mvar=gen_data["max_q_mvar"],
            slack=gen_data["slack"],
            state=GridGraphJsonMapper.deserialize_generator_state(gen_data["state"])
        )
    
    @staticmethod
    def deserialize_generator_state(state_data: dict) -> Generator:
        return GeneratorState(
            generator_id=state_data["generator_id"],
            p_mw=state_data["p_mw"],
            q_mvar=state_data["q_mvar"],
            cp1_eur_per_mw=state_data["cp1_eur_per_mw"]
        )

    @staticmethod
    def deserialize_load(load_data: dict) -> Load:
        return Load(
            id=load_data["id"],
            bus_id=load_data["bus_id"],
            load_type=LoadType(load_data["load_type"]),
            min_p_mw=load_data["min_p_mw"],
            max_p_mw=load_data["max_p_mw"],
            min_q_mvar=load_data["min_q_mvar"],
            max_q_mvar=load_data["max_q_mvar"],
            state=GridGraphJsonMapper.deserialize_load_state(load_data["state"])
        )
    
    @staticmethod
    def deserialize_load_state(state_data: dict) -> LoadState:
        return LoadState(
            load_id=state_data["load_id"],
            p_mw=state_data["p_mw"],
            q_mvar=state_data["q_mvar"]
        )

    @staticmethod
    def deserialize_der(der_data: dict) -> DER:
        return DER(
            id=der_data["id"],
            bus_id=der_data["bus_id"],
            der_type=DERType(der_data["der_type"]),
            min_p_mw=der_data["min_p_mw"],
            max_p_mw=der_data["max_p_mw"],
            min_q_mvar=der_data["min_q_mvar"],
            max_q_mvar=der_data["max_q_mvar"],
            state=GridGraphJsonMapper.deserialize_der_state(der_data["state"])
        )
    
    @staticmethod
    def deserialize_der_state(state_data: dict) -> DER:
        return DERState(
            der_id=state_data["der_id"],
            p_mw=state_data["p_mw"],
            q_mvar=state_data["q_mvar"]
        )
    
    @staticmethod
    def deserialize_storage_unit(unit_data: dict) -> DER:
        return StorageUnit(
            id=unit_data["id"],
            bus_id=unit_data["bus_id"],
            storage_type=StorageUnitType(unit_data["storage_type"]),
            min_p_mw=unit_data["min_p_mw"],
            max_p_mw=unit_data["max_p_mw"],
            min_q_mvar=unit_data["min_q_mvar"],
            max_q_mvar=unit_data["max_q_mvar"],
            min_e_mwh=unit_data["min_e_mwh"],
            max_e_mwh=unit_data["max_e_mwh"],
            state=GridGraphJsonMapper.deserialize_storage_unit_state(unit_data["state"])
        )
    
    @staticmethod
    def deserialize_storage_unit_state(state_data: dict) -> StorageUnitState:
        return StorageUnitState(
            storage_unit_id=state_data["storage_unit_id"],
            p_mw=state_data["p_mw"],
            q_mvar=state_data["q_mvar"],
            soc_percent=state_data["soc_percent"]
        )
    
    @staticmethod
    def serialize_grid_graph(graph: GridGraph) -> dict:
        return {
            "id": graph.id,
            "created_at": graph.created_at.isoformat(),
            "graph_data": GridGraphJsonMapper.serialize_graph_data(graph.graph_data)
        }

    @staticmethod
    def serialize_graph_data(graph_data: GridGraphData) -> dict:
        return {
            "buses": [GridGraphJsonMapper.serialize_bus(bus) for bus in graph_data.buses],
            "edges": [GridGraphJsonMapper.serialize_edge(edge) for edge in graph_data.edges]
        }

    @staticmethod
    def serialize_bus(bus: Bus) -> dict:
        return {
            "id": bus.id,
            "bus_type": bus.bus_type.value,
            "latitude": bus.latitude,
            "longitude": bus.longitude,
            "min_vm_pu": bus.min_vm_pu,
            "max_vm_pu": bus.max_vm_pu,
            "vn_kv": bus.vn_kv,
            "state": GridGraphJsonMapper.serialize_bus_state(bus.state),
            "generators": [GridGraphJsonMapper.serialize_generator(gen) for gen in bus.generators],
            "loads": [GridGraphJsonMapper.serialize_load(load) for load in bus.loads],
            "ders": [GridGraphJsonMapper.serialize_der(der) for der in bus.ders],
            "storage_units": [GridGraphJsonMapper.serialize_storage_unit(storage_unit) for storage_unit in bus.storage_units],
        }
    
    @staticmethod
    def serialize_bus_state(state: BusState) -> dict:
        return {
            "bus_id": state.bus_id,
            "vm_pu": state.vm_pu,
            "va_deg": state.va_deg,
            "p_inj_mw": state.p_inj_mw,
            "q_inj_mvar": state.q_inj_mvar,
            "tap_pos": state.tap_pos,
        }

    @staticmethod
    def serialize_generator(gen: Generator) -> dict:
        return {
            "id": gen.id,
            "bus_id": gen.bus_id,
            "generator_type": gen.generator_type.value,
            "min_p_mw": gen.min_p_mw,
            "max_p_mw": gen.max_p_mw,
            "min_q_mvar": gen.min_q_mvar,
            "max_q_mvar": gen.max_q_mvar,
            "slack": gen.slack,
            "state": GridGraphJsonMapper.serialize_generator_state(gen.state)
        }

    @staticmethod
    def serialize_generator_state(state: GeneratorState) -> dict:
        return {
            "generator_id": state.generator_id,
            "p_mw": state.p_mw,
            "q_mvar": state.q_mvar,
            "cp1_eur_per_mw": state.cp1_eur_per_mw,
        }

    @staticmethod
    def serialize_load(load: Load) -> dict:
        return {
            "id": load.id,
            "bus_id": load.bus_id,
            "load_type": load.load_type.value,
            "min_p_mw": load.min_p_mw,
            "max_p_mw": load.max_p_mw,
            "min_q_mvar": load.min_q_mvar,
            "max_q_mvar": load.max_q_mvar,
            "state": GridGraphJsonMapper.serialize_load_state(load.state)
        }
    
    @staticmethod
    def serialize_load_state(state: LoadState) -> dict:
        return {
            "load_id": state.load_id,
            "p_mw": state.p_mw,
            "q_mvar": state.q_mvar,
        }

    @staticmethod
    def serialize_der(gen: DER) -> dict:
        return {
            "id": gen.id,
            "bus_id": gen.bus_id,
            "der_type": gen.der_type.value,
            "min_p_mw": gen.min_p_mw,
            "max_p_mw": gen.max_p_mw,
            "min_q_mvar": gen.min_q_mvar,
            "max_q_mvar": gen.max_q_mvar,
            "state": GridGraphJsonMapper.serialize_der_state(gen.state)
        }

    @staticmethod
    def serialize_der_state(state: DERState) -> dict:
        return {
            "der_id": state.der_id,
            "p_mw": state.p_mw,
            "q_mvar": state.q_mvar,
        }
    
    @staticmethod
    def serialize_storage_unit(gen: StorageUnit) -> dict:
        return {
            "id": gen.id,
            "bus_id": gen.bus_id,
            "storage_type": gen.storage_type.value,
            "min_p_mw": gen.min_p_mw,
            "max_p_mw": gen.max_p_mw,
            "min_q_mvar": gen.min_q_mvar,
            "max_q_mvar": gen.max_q_mvar,
            "min_e_mwh": gen.min_e_mwh,
            "max_e_mwh": gen.max_e_mwh,
            "state": GridGraphJsonMapper.serialize_storage_unit_state(gen.state)
        }

    @staticmethod
    def serialize_storage_unit_state(state: StorageUnitState) -> dict:
        return {
            "storage_unit_id": state.storage_unit_id,
            "p_mw": state.p_mw,
            "q_mvar": state.q_mvar,
            "soc_percent": state.soc_percent,
        }

    @staticmethod
    def serialize_edge(edge: Edge) -> dict:
        return {
            "id": edge.id,
            "src_bus_id": edge.src_bus_id,
            "dest_bus_id": edge.dest_bus_id,
            "edge_type": edge.edge_type.value,
            "length_km": edge.length_km,
            "r_ohm_per_km": edge.r_ohm_per_km,
            "x_ohm_per_km": edge.x_ohm_per_km,
            "state": GridGraphJsonMapper.serialize_edge_state(edge.state)
        }
    
    @staticmethod
    def serialize_edge_state(state: EdgeState) -> dict:
        return {
            "edge_id": state.edge_id,
            "p_flow_mw": state.p_flow_mw,
            "q_flow_mvar": state.q_flow_mvar,
            "i_ka": state.i_ka,
            "in_service": state.in_service,
        }