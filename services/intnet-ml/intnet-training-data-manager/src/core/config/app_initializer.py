from dotenv import load_dotenv
from fastapi import FastAPI

from core.config.logging_config import configure_logging, get_logger
from features.dynamic_data_record.repositories.dynamic_data_record_repository_creator import create_dynamic_data_record_repository
from features.dynamic_data_record.routers.dynamic_data_record_router import create_record_router
from features.dynamic_data_record.services.record_generator_service import RecordGeneratorService
from features.grid_graph.repositories.grid_graph_repository_creator import create_grid_graph_repository
from features.grid_graph.routers.grid_graph_router import create_grid_graph_router
from shared.enums import GraphType, RecordType


def initialize_server():
    load_dotenv()
    configure_logging()
    logger = get_logger(__name__)
    logger.info("Initializing Training Data Manager Service")

def initialize_services(app: FastAPI):
    real_graph_repository = create_grid_graph_repository(GraphType.REAL)
    synthetic_graph_repository = create_grid_graph_repository(GraphType.SYNTHETIC)
    real_record_repository = create_dynamic_data_record_repository(RecordType.REAL)
    synthetic_record_repository = create_dynamic_data_record_repository(RecordType.SYNTHETIC)
    
    record_generator_service = RecordGeneratorService(synthetic_graph_repository, synthetic_record_repository)

    grid_graph_router = create_grid_graph_router(real_graph_repository)
    record_router = create_record_router(real_record_repository, record_generator_service)

    app.include_router(record_router)

    app.include_router(grid_graph_router)
        