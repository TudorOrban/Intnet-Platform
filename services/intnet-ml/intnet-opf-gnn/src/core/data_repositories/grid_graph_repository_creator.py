
import os
from core.data_repositories.grid_graph_repository import GridGraphRepository
from core.data_repositories.json.grid_graph_json_repository import GridGraphJsonRepository
from core.data_repositories.mongo.grid_graph_mongo_repository import GridGraphMongoRepository


def create_grid_graph_repository() -> GridGraphRepository:
    storage_type = os.getenv("STORAGE_TYPE", "json").lower()

    if storage_type == "mongo":
        mongoRepository = GridGraphMongoRepository()
        if not mongoRepository.client:
            raise Exception("Failed to establish connection to MongoDB!")
        return mongoRepository
    else:
        return GridGraphJsonRepository()