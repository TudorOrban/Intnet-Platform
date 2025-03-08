
import os
from bson import ObjectId
import structlog
from typing import List, Optional
from pymongo import MongoClient
from pymongo.errors import ConnectionFailure

from features.grid_graph.models.grid_graph_types import GridGraph
from features.grid_graph.repositories.grid_graph_repository import GridGraphRepository
from features.grid_graph.utils.grid_graph_json_mapper import GridGraphJsonMapper
from shared.enums import GraphType
from shared.utils import mongo_connection_check

logger = structlog.get_logger(__name__)

class GridGraphMongoRepository(GridGraphRepository):
    def __init__(self, graph_type: GraphType):
        connection_string = os.getenv("MONGODB_CONNECTION_STRING")
        database_name = os.getenv("MONGODB_DATABASE_NAME")
        collection_name = "synthetic_grid_graph" if graph_type == GraphType.SYNTHETIC else "grid_graph"

        try:
            self.client = MongoClient(connection_string)
            self.db = self.client[database_name]
            self.collection = self.db[collection_name]
            self.client.admin.command("ping")
        except ConnectionFailure as e:
            logger.error(f"Could not connect to MongoDB: {e}")
            self.client = None
            self.db = None
            self.collection = None

    @mongo_connection_check
    def find_by_grid_id(self, grid_id: int) -> Optional[GridGraph]:
        document = self.collection.find_one({"grid_id": grid_id})
        if document:
            return GridGraphJsonMapper.deserialize_grid_graph(document)
        else:
            return None
    
    @mongo_connection_check
    def find_all(self) -> Optional[GridGraph]:
        document = self.collection.find_one()
        
        results = self.collection.find()
        graphs = []
        for i, result in enumerate(results):
            result.pop("_id", None) 
            graphs.append(GridGraphJsonMapper.deserialize_grid_graph(document))
        return graphs

    @mongo_connection_check
    def add(self, grid_graph: GridGraph):
        document = GridGraphJsonMapper.serialize_grid_graph(grid_graph)
        new_object_id = ObjectId()
        document["_id"] = new_object_id
        document["id"] = self.generate_unique_integer_id()
        self.collection.insert_one(document)

    @mongo_connection_check
    def add_all(self, grid_graphs: List[GridGraph]):
        document = [GridGraphJsonMapper.serialize_grid_graph(grid_graph) for grid_graph in grid_graphs]
        for graph_dict in document:
            new_object_id = ObjectId()
            graph_dict["_id"] = new_object_id
        graph_dict["id"] = self.generate_unique_integer_id()
        self.collection.insert_many(document)

    @mongo_connection_check
    def update(self, grid_graph: GridGraph):
        document = GridGraphJsonMapper.serialize_grid_graph(grid_graph)
        grid_id = document.get("grid_id")

        if grid_id is None:
            logger.error("Failed to update grid graph: Missing Grid ID")
            return

        result = self.collection.replace_one({"grid_id": grid_id}, document)

        if result.modified_count == 0:
            logger.warning(f"No document updated for grid_id: {grid_id}")
        else:
            logger.info(f"Document updated for grid_id: {grid_id}")
    
    @mongo_connection_check
    def delete_by_grid_id(self, grid_id: int):
        self.collection.delete_one({"grid_id": grid_id})

    @mongo_connection_check
    def delete_all(self):
        self.collection.delete_many({})

    @mongo_connection_check
    def _get_existing_ids(self) -> set:
        existing_ids = self.collection.distinct("id")
        return set(existing_ids)

    def generate_unique_integer_id(self) -> int:
        existing_ids = self._get_existing_ids()
        import random
        while True:
            new_id = random.randint(1, 2147483647)
            if new_id not in existing_ids:
                return new_id