from datetime import datetime
import logging
from fastapi import FastAPI
from dotenv import load_dotenv

from features.grid_graph.models.grid_graph_types import GridGraph, GridGraphData
from features.grid_graph.repositories.grid_graph_repository_creator import create_grid_graph_repository

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

load_dotenv()

app = FastAPI()

@app.get("/")
async def root():
    return {"message": "Intnet Training Data Manager Service is running"}

@app.get("/grid-graph")
async def get_grid_graph():
    graph_repository = create_grid_graph_repository()

    grid_graph = GridGraph(id=0, created_at=datetime.now(), graph_data=GridGraphData(buses=[], edges=[]))

    graph_repository.save(grid_graph)

    saved_graph = graph_repository.find()
    
    return saved_graph