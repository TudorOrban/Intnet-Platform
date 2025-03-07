
import os
import structlog
from typing import Optional
from pymongo import MongoClient
from pymongo.errors import ConnectionFailure

from features.grid_graph.models.grid_graph_types import GridGraph
from features.grid_graph.repositories.grid_graph_repository import GridGraphRepository
from features.grid_graph.utils.grid_graph_json_mapper import GridGraphJsonMapper

logger = structlog.get_logger(__name__)

class GridGraphMongoRepository(GridGraphRepository):
    def __init__(self):
        print(os.getenv("MONGODB_CONNECTION_STRING"))
        connection_string = os.getenv("MONGODB_CONNECTION_STRING")
        database_name = os.getenv("MONGODB_DATABASE_NAME")
        collection_name="grid_graph"

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

    
    def find(self) -> Optional[GridGraph]:
        if self.collection is None:
            logger.error("MongoDB connection is not established.")
            return None

        document = self.collection.find_one()
        print("Document", document)
        if document:
            return GridGraphJsonMapper.deserialize_grid_graph(document)
        else:
            return None

    def save(self, grid_graph: GridGraph):
        if self.collection is None:
            logger.error("MongoDB connection is not established.")
            return
        
        document = GridGraphJsonMapper.serialize_grid_graph(grid_graph)
        self.collection.delete_many({})
        self.collection.insert_one(document)

    def delete(self, id: int):
        if self.collection is None:
            logger.error("MongoDB connection is not established.")
            return

        self.collection.delete_many({})