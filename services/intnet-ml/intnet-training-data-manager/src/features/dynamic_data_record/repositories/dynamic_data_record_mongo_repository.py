
import os
import structlog
from bson.objectid import ObjectId
from typing import List, Optional
from pymongo import MongoClient
from pymongo.errors import ConnectionFailure

from features.dynamic_data_record.models.record_types import DynamicDataRecord
from features.dynamic_data_record.repositories.dynamic_data_record_repository import DynamicDataRecordRepository
from features.dynamic_data_record.utils.dynamic_data_record_json_mapper import DynamicDataRecordJsonMapper


logger = structlog.get_logger(__name__)

class DynamicDataRecordMongoRepository(DynamicDataRecordRepository):
    def __init__(self):
        connection_string = os.getenv("MONGODB_CONNECTION_STRING")
        database_name = os.getenv("MONGODB_DATABASE_NAME")
        collection_name="dynamic_data_records"

        try:
            self.client = MongoClient(connection_string)
            self.db = self.client[database_name]
            self.collection = self.db[collection_name]
            self.client.admin.command("ping")
            logger.info("Successfully connected to MongoDB.")
        except ConnectionFailure as e:
            logger.error(f"Could not connect to MongoDB: {e}")
            self.client = None
            self.db = None
            self.collection = None

    
    def find_by_id(self, id: int) -> Optional[DynamicDataRecord]:
        if self.collection is None:
            logger.error("MongoDB connection is not established.")
            return None

        result = self.collection.find_one({"id": id})
        if result:
            result.pop("_id", None) 
            return DynamicDataRecordJsonMapper.deserialize_dynamic_data_record(result)
        else:
            return None

    def find_all(self, limit=1000) -> List[DynamicDataRecord]:
        if self.collection is None:
            logger.error("MongoDB connection is not established.")
            return []

        results = self.collection.find()
        records = []
        for i, result in enumerate(results):
            if limit != -1 and i > limit:
                break

            result.pop("_id", None) 
            records.append(DynamicDataRecordJsonMapper.deserialize_dynamic_data_record(result))
        return records

    def save(self, record: DynamicDataRecord):
        if self.collection is None:
            logger.error("MongoDB connection is not established.")
            return

        record_dict = DynamicDataRecordJsonMapper.serialize_dynamic_data_record(record)
        new_object_id = ObjectId()
        record_dict["_id"] = new_object_id
        record_dict["id"] = self.generate_unique_integer_id()
        self.collection.insert_one(record_dict)

    def save_all(self, records: List[DynamicDataRecord]):
        if self.collection is None:
            logger.error("MongoDB connection is not established.")
            return
        
        record_dicts = [DynamicDataRecordJsonMapper.serialize_dynamic_data_record(record) for record in records]
        for record_dict in record_dicts:
            new_object_id = ObjectId()
            record_dict["_id"] = new_object_id
        record_dict["id"] = self.generate_unique_integer_id()
        self.collection.insert_many(record_dicts)

    def delete_by_id(self, id: int):
        if self.collection is None:
            logger.error("MongoDB connection is not established.")
            return

        self.collection.delete_one({"id": id})
    
    def delete_all(self):
        if self.collection is None:
            logger.error("MongoDB connection is not established.")
            return
        
        self.collection.delete_many({})

    
    def _get_existing_ids(self) -> set:
        if self.collection is None:
            logger.error("MongoDB connection is not established.")
            return set()

        existing_ids = self.collection.distinct("id")
        return set(existing_ids)

    def generate_unique_integer_id(self) -> int:
        existing_ids = self._get_existing_ids()
        import random
        while True:
            new_id = random.randint(1, 2147483647)
            if new_id not in existing_ids:
                return new_id