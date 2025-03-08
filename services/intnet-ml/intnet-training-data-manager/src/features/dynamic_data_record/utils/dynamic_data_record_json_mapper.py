from features.dynamic_data_record.models.record_types import DynamicDataRecord, RecordData
from features.grid_graph.utils.grid_graph_json_mapper import GridGraphJsonMapper


class DynamicDataRecordJsonMapper:

    @staticmethod
    def deserialize_dynamic_data_record(record: dict) -> DynamicDataRecord:
        return DynamicDataRecord(
            id=record["id"],
            created_at=record["created_at"],
            record_data=DynamicDataRecordJsonMapper.deserialize_record_data(record["record_data"])
        )
    
    @staticmethod
    def deserialize_record_data(record_data: dict) -> RecordData:
        bus_data = {
            int(bus_id): GridGraphJsonMapper.deserialize_bus_state(state_data)
            for bus_id, state_data in record_data["bus_data"].items()
        }

        edge_data = {
            int(edge_id): GridGraphJsonMapper.deserialize_edge_state(state_data)
            for edge_id, state_data in record_data["edge_data"].items()
        }

        generator_data = {
            int(generator_id): GridGraphJsonMapper.deserialize_generator_state(state_data)
            for generator_id, state_data in record_data["generator_data"].items()
        }

        load_data = {
            int(load_id): GridGraphJsonMapper.deserialize_load_state(state_data)
            for load_id, state_data in record_data["load_data"].items()
        }

        der_data = {
            int(der_id): GridGraphJsonMapper.deserialize_der_state(state_data)
            for der_id, state_data in record_data["der_data"].items()
        }

        storage_unit_data = {
            int(storage_unit_id): GridGraphJsonMapper.deserialize_storage_unit_state(state_data)
            for storage_unit_id, state_data in record_data["storage_unit_data"].items()
        }

        return RecordData(
            bus_data=bus_data,
            edge_data=edge_data,
            generator_data=generator_data,
            load_data=load_data,
            der_data=der_data,
            storage_unit_data=storage_unit_data,
        )
    
    @staticmethod
    def serialize_dynamic_data_record(record: DynamicDataRecord) -> dict:
        return {
            "id": record.id,
            "created_at": record.created_at,
            "record_data": DynamicDataRecordJsonMapper.serialize_record_data(record.record_data)
        }

    @staticmethod
    def serialize_record_data(record_data: RecordData) -> dict:
        bus_data = {
            str(bus_id): GridGraphJsonMapper.serialize_bus_state(state)
            for bus_id, state in record_data.bus_data.items()
        }

        edge_data = {
            str(edge_id): GridGraphJsonMapper.serialize_edge_state(state)
            for edge_id, state in record_data.edge_data.items()
        }

        generator_data = {
            str(generator_id): GridGraphJsonMapper.serialize_generator_state(state)
            for generator_id, state in record_data.generator_data.items()
        }

        load_data = {
            str(load_id): GridGraphJsonMapper.serialize_load_state(state)
            for load_id, state in record_data.load_data.items()
        }

        der_data = {
            str(der_id): GridGraphJsonMapper.serialize_der_state(state)
            for der_id, state in record_data.der_data.items()
        }

        storage_unit_data = {
            str(storage_unit_id): GridGraphJsonMapper.serialize_storage_unit_state(state)
            for storage_unit_id, state in record_data.storage_unit_data.items()
        }

        return {
            "bus_data": bus_data,
            "edge_data": edge_data,
            "generator_data": generator_data,
            "load_data": load_data,
            "der_data": der_data,
            "storage_unit_data": storage_unit_data,
        }