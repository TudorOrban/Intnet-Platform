import os
from fastapi import FastAPI
from dotenv import load_dotenv

from core.config.logging_config import configure_logging, get_logger
from features.grid_graph.routers.grid_graph_router import router as grid_graph_router # Correct import

load_dotenv()


configure_logging()
logger = get_logger(__name__)

app = FastAPI()



@app.get("/")
async def root():
    return {"message": "Intnet Training Data Manager Service is running"}

app.include_router(grid_graph_router)