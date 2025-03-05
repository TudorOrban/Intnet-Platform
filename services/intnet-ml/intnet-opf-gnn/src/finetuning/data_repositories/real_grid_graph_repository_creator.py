
import os

from finetuning.data_repositories.json.real_grid_graph_json_repository import RealGridGraphJsonRepository
from finetuning.data_repositories.real_grid_graph_repository import RealGridGraphRepository

def create_real_grid_graph_repository() -> RealGridGraphRepository:
    storage_type = os.getenv("STORAGE_TYPE", "json").lower()

    if storage_type == "mongo":
        # mongoRepository = GridGraphMongoRepository()
        # if not mongoRepository.client:
        #     raise Exception("Failed to establish connection to MongoDB!")
        # return mongoRepository
        raise Exception("Mongo repository not yet implemented.")
    else:
        return RealGridGraphJsonRepository()