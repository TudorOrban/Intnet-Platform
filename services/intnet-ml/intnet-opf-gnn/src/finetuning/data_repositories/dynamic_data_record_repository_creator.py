
import os

from finetuning.data_repositories.json.dynamic_data_record_json_repository import DynamicDataRecordJsonRepository
from finetuning.data_repositories.dynamic_data_record_repository import DynamicDataRecordRepository

def create_dynamic_data_record_repository() -> DynamicDataRecordRepository:
    storage_type = os.getenv("STORAGE_TYPE", "json").lower()

    if storage_type == "mongo":
        # mongoRepository = GridGraphMongoRepository()
        # if not mongoRepository.client:
        #     raise Exception("Failed to establish connection to MongoDB!")
        # return mongoRepository
        raise Exception("Mongo repository not yet implemented.")
    else:
        return DynamicDataRecordJsonRepository()