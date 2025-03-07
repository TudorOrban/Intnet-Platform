from fastapi import APIRouter

from features.grid_graph.models.grid_graph_types import GridGraph
from features.grid_graph.repositories.grid_graph_repository_creator import create_grid_graph_repository


router = APIRouter()

grid_graph_base_route = "/api/v1/grid-graph"
  
graph_repository = create_grid_graph_repository()

@router.get(grid_graph_base_route)
async def get_grid_graph():
    grid_graph = graph_repository.find()
    return grid_graph

@router.post(grid_graph_base_route)
async def update_grid_graph(grid_graph: GridGraph):
    print("Test")
    graph_repository.save(grid_graph)
    

@router.delete(grid_graph_base_route)
async def delete_grid_graph():
    graph_repository.delete()