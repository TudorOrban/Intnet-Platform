
import os

from features.dynamic_data_record.repositories.dynamic_data_record_json_repository import DynamicDataRecordJsonRepository
from features.dynamic_data_record.repositories.dynamic_data_record_mongo_repository import DynamicDataRecordMongoRepository
from features.dynamic_data_record.repositories.dynamic_data_record_repository import DynamicDataRecordRepository

def create_dynamic_data_record_repository() -> DynamicDataRecordRepository:
    storage_type = os.getenv("STORAGE_TYPE", "json").lower()

    if storage_type == "mongo":
        mongoRepository = DynamicDataRecordMongoRepository()
        if not mongoRepository.client:
            raise Exception("Failed to establish connection to MongoDB!")
        return mongoRepository
    else:
        return DynamicDataRecordJsonRepository()