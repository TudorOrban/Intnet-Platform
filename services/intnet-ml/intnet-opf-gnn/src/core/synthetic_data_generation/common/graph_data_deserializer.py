from core.synthetic_data_generation.base.data_generators.solution_generator.sample_types import BusFixedSpecificationSamples, EdgeFixedSpecificationSamples, FixedSpecificationSample, FixedTopologySample, GeneratorFixedSpecificationSamples, LoadFixedSpecificationSamples
from core.synthetic_data_generation.common.data_types import GridGraph, GridGraphData, Bus, Edge, Generator, Load, BusState, EdgeState, GeneratorState, LoadState, BusType


class GraphDataDeserializer:

    # Graph Data
    @staticmethod
    def deserialize_graph_data(graph_data: dict) -> GridGraphData:
        return GridGraphData(
            buses=[GraphDataDeserializer.deserialize_bus(bus_data) for bus_data in graph_data["buses"]],
            edges=[GraphDataDeserializer.deserialize_edge(edge_data) for edge_data in graph_data["edges"]]
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
            state=BusState(
                vm_pu=bus_data["state"]["vm_pu"],
                va_deg=bus_data["state"]["va_deg"],
                p_inj_mw=bus_data["state"]["p_inj_mw"],
                q_inj_mvar=bus_data["state"]["q_inj_mvar"],
                tap_pos=bus_data["state"]["tap_pos"]
            ),
            generators=[GraphDataDeserializer.deserialize_generator(gen_data) for gen_data in bus_data["generators"]],
            loads=[GraphDataDeserializer.deserialize_load(load_data) for load_data in bus_data["loads"]]
        )

    @staticmethod
    def deserialize_edge(edge_data: dict) -> Edge:
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

    @staticmethod
    def deserialize_generator(gen_data: dict) -> Generator:
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

    @staticmethod
    def deserialize_load(load_data: dict) -> Load:
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

    @staticmethod
    def serialize_graph(graph: GridGraph) -> dict:
        return {
            "id": graph.id,
            "created_at": graph.created_at.isoformat(),
            "graph_data": GraphDataDeserializer.serialize_graph_data(graph.graph_data)
        }

    @staticmethod
    def serialize_graph_data(graph_data: GridGraphData) -> dict:
        return {
            "buses": [GraphDataDeserializer.serialize_bus(bus) for bus in graph_data.buses],
            "edges": [GraphDataDeserializer.serialize_edge(edge) for edge in graph_data.edges]
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
            "state": {
                "vm_pu": bus.state.vm_pu,
                "va_deg": bus.state.va_deg,
                "p_inj_mw": bus.state.p_inj_mw,
                "q_inj_mvar": bus.state.q_inj_mvar,
                "tap_pos": bus.state.tap_pos
            },
            "generators": [GraphDataDeserializer.serialize_generator(gen) for gen in bus.generators],
            "loads": [GraphDataDeserializer.serialize_load(load) for load in bus.loads]
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
            "state": {
                "p_flow_mw": edge.state.p_flow_mw,
                "q_flow_mvar": edge.state.q_flow_mvar,
                "i_ka": edge.state.i_ka,
                "in_service": edge.state.in_service
            }
        }

    @staticmethod
    def serialize_generator(gen: Generator) -> dict:
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

    @staticmethod
    def serialize_load(load: Load) -> dict:
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
    
    # Samples
    @staticmethod
    def deserialize_fixed_topology_sample(sample_data: dict) -> FixedTopologySample:
        graph_topology = GraphDataDeserializer.deserialize_graph_data(sample_data["graph_topology"])
        fixed_specification_samples = [GraphDataDeserializer.deserialize_fixed_specification_sample(spec_data) for spec_data in sample_data["fixed_specification_samples"]]

        return FixedTopologySample(graph_topology=graph_topology, fixed_specification_samples=fixed_specification_samples)

    @staticmethod
    def deserialize_fixed_specification_sample(spec_data: dict) -> FixedSpecificationSample:
        bus_samples = {int(bus_id): GraphDataDeserializer.deserialize_bus_fixed_specification_samples(bus_data) for bus_id, bus_data in spec_data["bus_samples"].items()}
        edge_samples = {int(edge_id): GraphDataDeserializer.deserialize_edge_fixed_specification_samples(edge_data) for edge_id, edge_data in spec_data["edge_samples"].items()}
        generator_samples = {int(gen_id): GraphDataDeserializer.deserialize_generator_fixed_specification_samples(gen_data) for gen_id, gen_data in spec_data["generator_samples"].items()}
        load_samples = {int(load_id): GraphDataDeserializer.deserialize_load_fixed_specification_samples(load_data) for load_id, load_data in spec_data["load_samples"].items()}

        return FixedSpecificationSample(bus_samples=bus_samples, edge_samples=edge_samples, generator_samples=generator_samples, load_samples=load_samples)

    @staticmethod
    def deserialize_bus_fixed_specification_samples(bus_data: dict) -> BusFixedSpecificationSamples:
        bus = GraphDataDeserializer.deserialize_bus(bus_data["bus"])
        bus_states = [GraphDataDeserializer.deserialize_bus_state(state_data) for state_data in bus_data["bus_states"]]
        return BusFixedSpecificationSamples(bus=bus, bus_states=bus_states)

    @staticmethod
    def deserialize_edge_fixed_specification_samples(edge_data: dict) -> EdgeFixedSpecificationSamples:
        edge = GraphDataDeserializer.deserialize_edge(edge_data["edge"])
        edge_states = [GraphDataDeserializer.deserialize_edge_state(state_data) for state_data in edge_data["edge_states"]]
        return EdgeFixedSpecificationSamples(edge=edge, edge_states=edge_states)

    @staticmethod
    def deserialize_generator_fixed_specification_samples(gen_data: dict) -> GeneratorFixedSpecificationSamples:
        generator = GraphDataDeserializer.deserialize_generator(gen_data["generator"])
        generator_states = [GraphDataDeserializer.deserialize_generator_state(state_data) for state_data in gen_data["generator_states"]]
        return GeneratorFixedSpecificationSamples(generator=generator, generator_states=generator_states)

    @staticmethod
    def deserialize_load_fixed_specification_samples(load_data: dict) -> LoadFixedSpecificationSamples:
        load = GraphDataDeserializer.deserialize_load(load_data["load"])
        load_states = [GraphDataDeserializer.deserialize_load_state(state_data) for state_data in load_data["load_states"]]
        return LoadFixedSpecificationSamples(load=load, load_states=load_states)
