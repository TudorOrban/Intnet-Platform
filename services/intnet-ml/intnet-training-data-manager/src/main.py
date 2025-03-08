from contextlib import asynccontextmanager
from fastapi import FastAPI
from dotenv import load_dotenv

from core.config.logging_config import configure_logging, get_logger
from core.config.service_manager import initialize_services
from features.grid_graph.routers.grid_graph_router import router as grid_graph_router
from features.dynamic_data_record.routers.dynamic_data_record_router import router as dynamic_data_record_router

@asynccontextmanager
async def lifespan(app: FastAPI):
    global logger
    load_dotenv()
    configure_logging()
    logger = get_logger(__name__)
    initialize_services()
    yield
    
app = FastAPI(lifespan=lifespan)

@app.get("/")
async def root():
    return {"message": "Intnet Training Data Manager Service is running"}

app.include_router(grid_graph_router)
app.include_router(dynamic_data_record_router)
