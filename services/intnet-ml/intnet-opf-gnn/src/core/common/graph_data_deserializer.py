from core.data_generators.sample_manager.sample_types import BusFixedSpecificationSamples, EdgeFixedSpecificationSamples, FixedSpecificationSample, FixedTopologySample, GeneratorFixedSpecificationSamples, LoadFixedSpecificationSamples
from core.common.data_types import GridGraph, GridGraphData, Bus, Edge, Generator, Load, BusState, EdgeState, GeneratorState, LoadState, BusType


class GraphDataDeserializer:

    # Graph Data
    @staticmethod
    def deserialize_grid_graph(grid_graph: dict) -> GridGraph:
        return GridGraph(
            id=grid_graph["id"],
            created_at=grid_graph["created_at"],
            graph_data=GraphDataDeserializer.deserialize_graph_data(grid_graph["graph_data"])
        )

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
            state=GraphDataDeserializer.deserialize_bus_state(bus_data["state"]),
            generators=[GraphDataDeserializer.deserialize_generator(gen_data) for gen_data in bus_data["generators"]],
            loads=[GraphDataDeserializer.deserialize_load(load_data) for load_data in bus_data["loads"]]
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
            edge_type=edge_data["edge_type"],
            length_km=edge_data["length_km"],
            r_ohm_per_km=edge_data["r_ohm_per_km"],
            x_ohm_per_km=edge_data["x_ohm_per_km"],
            state=GraphDataDeserializer.deserialize_edge_state(edge_data["state"])
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
            min_p_mw=gen_data["min_p_mw"],
            max_p_mw=gen_data["max_p_mw"],
            min_q_mvar=gen_data["min_q_mvar"],
            max_q_mvar=gen_data["max_q_mvar"],
            slack=gen_data["slack"],
            state=GraphDataDeserializer.deserialize_generator_state(gen_data["state"])
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
            min_p_mw=load_data["min_p_mw"],
            max_p_mw=load_data["max_p_mw"],
            min_q_mvar=load_data["min_q_mvar"],
            max_q_mvar=load_data["max_q_mvar"],
            state=GraphDataDeserializer.deserialize_load_state(load_data["state"])
        )

    @staticmethod
    def deserialize_load_state(state_data: dict) -> LoadState:
        return LoadState(
            load_id=state_data["load_id"],
            p_mw=state_data["p_mw"],
            q_mvar=state_data["q_mvar"]
        )

    @staticmethod
    def serialize_grid_graph(graph: GridGraph) -> dict:
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
            "state": GraphDataDeserializer.serialize_bus_state(bus.state),
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
            "state": GraphDataDeserializer.serialize_edge_state(edge.state)
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
            "state": GraphDataDeserializer.serialize_generator_state(gen.state)
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
            "state": GraphDataDeserializer.serialize_load_state(load.state)
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
    def serialize_edge_state(state: EdgeState) -> dict:
        return {
            "edge_id": state.edge_id,
            "p_flow_mw": state.p_flow_mw,
            "q_flow_mvar": state.q_flow_mvar,
            "i_ka": state.i_ka,
            "in_service": state.in_service,
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
    def serialize_load_state(state: LoadState) -> dict:
        return {
            "load_id": state.load_id,
            "p_mw": state.p_mw,
            "q_mvar": state.q_mvar,
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

    @staticmethod
    def serialize_fixed_topology_sample(sample: FixedTopologySample) -> dict:
        return {
            "graph_topology": GraphDataDeserializer.serialize_graph_data(sample.graph_topology),
            "fixed_specification_samples": [
                GraphDataDeserializer.serialize_fixed_specification_sample(spec)
                for spec in sample.fixed_specification_samples
            ],
        }

    @staticmethod
    def serialize_fixed_specification_sample(spec: FixedSpecificationSample) -> dict:
        return {
            "bus_samples": {
                bus_id: GraphDataDeserializer.serialize_bus_fixed_specification_samples(bus_spec)
                for bus_id, bus_spec in spec.bus_samples.items()
            },
            "edge_samples": {
                edge_id: GraphDataDeserializer.serialize_edge_fixed_specification_samples(edge_spec)
                for edge_id, edge_spec in spec.edge_samples.items()
            },
            "generator_samples": {
                gen_id: GraphDataDeserializer.serialize_generator_fixed_specification_samples(gen_spec)
                for gen_id, gen_spec in spec.generator_samples.items()
            },
            "load_samples": {
                load_id: GraphDataDeserializer.serialize_load_fixed_specification_samples(load_spec)
                for load_id, load_spec in spec.load_samples.items()
            },
        }

    @staticmethod
    def serialize_bus_fixed_specification_samples(bus_spec: BusFixedSpecificationSamples) -> dict:
        return {
            "bus": GraphDataDeserializer.serialize_bus(bus_spec.bus),
            "bus_states": [GraphDataDeserializer.serialize_bus_state(state) for state in bus_spec.bus_states],
        }

    @staticmethod
    def serialize_edge_fixed_specification_samples(edge_spec: EdgeFixedSpecificationSamples) -> dict:
        return {
            "edge": GraphDataDeserializer.serialize_edge(edge_spec.edge),
            "edge_states": [GraphDataDeserializer.serialize_edge_state(state) for state in edge_spec.edge_states],
        }

    @staticmethod
    def serialize_generator_fixed_specification_samples(gen_spec: GeneratorFixedSpecificationSamples) -> dict:
        return {
            "generator": GraphDataDeserializer.serialize_generator(gen_spec.generator),
            "generator_states": [
                GraphDataDeserializer.serialize_generator_state(state) for state in gen_spec.generator_states
            ],
        }

    @staticmethod
    def serialize_load_fixed_specification_samples(load_spec: LoadFixedSpecificationSamples) -> dict:
        return {
            "load": GraphDataDeserializer.serialize_load(load_spec.load),
            "load_states": [GraphDataDeserializer.serialize_load_state(state) for state in load_spec.load_states],
        }