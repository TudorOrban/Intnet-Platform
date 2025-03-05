
import os
import structlog
from bson.objectid import ObjectId
from typing import List, Optional
from pymongo import MongoClient
from pymongo.errors import ConnectionFailure

from core.data_repositories.grid_graph_repository import GridGraphRepository
from core.common.data_types import GridGraph
from core.common.json_serializer import JsonSerializer

logger = structlog.get_logger(__name__)

class GridGraphMongoRepository(GridGraphRepository):
    def __init__(self):
        connection_string = os.getenv("MONGODB_CONNECTION_STRING")
        database_name = os.getenv("MONGODB_DATABASE_NAME")
        collection_name="grid_graphs"

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

    
    def find_by_id(self, id: int) -> Optional[GridGraph]:
        if self.collection is None:
            logger.error("MongoDB connection is not established.")
            return None

        result = self.collection.find_one({"id": id})
        if result:
            result.pop("_id", None) 
            return JsonSerializer.deserialize_dataclass(result, GridGraph)
        else:
            return None

    def find_all(self, limit=1000) -> List[GridGraph]:
        if self.collection is None:
            logger.error("MongoDB connection is not established.")
            return []

        results = self.collection.find()
        grid_graphs = []
        for i, result in enumerate(results):
            if limit != -1 and i > limit:
                break

            result.pop("_id", None) 
            grid_graphs.append(JsonSerializer.deserialize_dataclass(result, GridGraph))
        return grid_graphs

    def save(self, grid_graph: GridGraph):
        if self.collection is None:
            logger.error("MongoDB connection is not established.")
            return

        graph_dict = JsonSerializer.serialize_dataclass(grid_graph)
        new_object_id = ObjectId()
        graph_dict["_id"] = new_object_id
        graph_dict["id"] = self.generate_unique_integer_id()
        self.collection.insert_one(graph_dict)

    def save_all(self, grid_graphs: List[GridGraph]):
        if self.collection is None:
            logger.error("MongoDB connection is not established.")
            return
        
        graph_dicts = [JsonSerializer.serialize_dataclass(graph) for graph in grid_graphs]
        for graph_dict in graph_dicts:
            new_object_id = ObjectId()
            graph_dict["_id"] = new_object_id
        graph_dict["id"] = self.generate_unique_integer_id()
        self.collection.insert_many(graph_dicts)

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