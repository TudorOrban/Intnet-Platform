from dotenv import load_dotenv
from fastapi import FastAPI

from core.config.logging_config import configure_logging, get_logger
from features.dynamic_data_record.repositories.dynamic_data_record_repository_creator import create_dynamic_data_record_repository
from features.dynamic_data_record.routers.dynamic_data_record_router import create_record_router
from features.grid_graph.repositories.grid_graph_repository_creator import create_grid_graph_repository
from features.grid_graph.routers.grid_graph_router import create_grid_graph_router


def initialize_server():
    load_dotenv()
    configure_logging()
    logger = get_logger(__name__)
    logger.info("Initializing Training Data Manager Service")

def initialize_routers(app: FastAPI):
    grid_graph_router = create_grid_graph_router(create_grid_graph_repository())
    record_router = create_record_router(create_dynamic_data_record_repository())

    app.include_router(record_router)

    app.include_router(grid_graph_router)
        