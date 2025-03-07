
import os

# from features.dynamic_data_record.repositories.dynamic_data_record_json_repository import GridGraphJsonRepository
from features.dynamic_data_record.repositories.dynamic_data_record_mongo_repository import GridGraphMongoRepository
from features.dynamic_data_record.repositories.dynamic_data_record_repository import GridGraphRepository


def create_dynamic_data_record_repository() -> GridGraphRepository:
    storage_type = os.getenv("STORAGE_TYPE", "json").lower()

    # if storage_type == "mongo":
    mongoRepository = GridGraphMongoRepository()
    if not mongoRepository.client:
        raise Exception("Failed to establish connection to MongoDB!")
    return mongoRepository
    # else:
    #     return GridGraphJsonRepository()