
import os

from features.dynamic_data_record.repositories.dynamic_data_record_json_repository import DynamicDataRecordJsonRepository
from features.dynamic_data_record.repositories.dynamic_data_record_mongo_repository import DynamicDataRecordMongoRepository
from features.dynamic_data_record.repositories.dynamic_data_record_repository import DynamicDataRecordRepository
from shared.enums import RecordType


def create_dynamic_data_record_repository(record_type: RecordType) -> DynamicDataRecordRepository:
    storage_type = os.getenv("STORAGE_TYPE", "json").lower()

    if storage_type == "mongo":
        mongo_repository = DynamicDataRecordMongoRepository(record_type)
        if not mongo_repository.client:
            raise Exception("Failed to establish connection to MongoDB!")
        return mongo_repository
    else:
        return DynamicDataRecordJsonRepository(record_type)