

from core.common.graph_data_deserializer import GraphDataDeserializer
from finetuning.common.data_types import DynamicDataRecord


class DynamicDataRecordDeserializer:

    # Graph Data
    @staticmethod
    def deserialize_dynamic_data_record(record: dict) -> DynamicDataRecord:
        bus_data = {
            int(bus_id): GraphDataDeserializer.deserialize_bus_state(state_data)
            for bus_id, state_data in record["bus_data"].items()
        }

        edge_data = {
            int(edge_id): GraphDataDeserializer.deserialize_edge_state(state_data)
            for edge_id, state_data in record["edge_data"].items()
        }

        generator_data = {
            int(generator_id): GraphDataDeserializer.deserialize_generator_state(state_data)
            for generator_id, state_data in record["generator_data"].items()
        }

        load_data = {
            int(load_id): GraphDataDeserializer.deserialize_load_state(state_data)
            for load_id, state_data in record["load_data"].items()
        }

        return DynamicDataRecord(
            bus_data=bus_data,
            edge_data=edge_data,
            generator_data=generator_data,
            load_data=load_data
        )