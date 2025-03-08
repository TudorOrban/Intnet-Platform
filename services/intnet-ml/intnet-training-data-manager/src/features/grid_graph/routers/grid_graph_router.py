from fastapi import APIRouter

from core.config.service_manager import get_service
from features.grid_graph.models.grid_graph_types import GridGraph

router = APIRouter()

grid_graph_base_route = "/api/v1/grid-graph"

@router.get(grid_graph_base_route)
async def get_grid_graph():
    grid_graph = get_service("grid_graph_repository").find()
    return grid_graph

@router.post(grid_graph_base_route)
async def update_grid_graph(grid_graph: GridGraph):
    print("Test")
    get_service("grid_graph_repository").save(grid_graph)
    

@router.delete(grid_graph_base_route)
async def delete_grid_graph():
    get_service("grid_graph_repository").delete()