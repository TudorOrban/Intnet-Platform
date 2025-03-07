
import os

from features.grid_graph.repositories.grid_graph_json_repository import GridGraphJsonRepository
from features.grid_graph.repositories.grid_graph_repository import GridGraphRepository


def create_grid_graph_repository() -> GridGraphRepository:
    storage_type = os.getenv("STORAGE_TYPE", "json").lower()

    if storage_type == "mongo":
        # mongoRepository = GridGraphMongoRepository()
        # if not mongoRepository.client:
        #     raise Exception("Failed to establish connection to MongoDB!")
        # return mongoRepository
        raise Exception("Mongo repository not yet implemented.")
    else:
        return GridGraphJsonRepository()