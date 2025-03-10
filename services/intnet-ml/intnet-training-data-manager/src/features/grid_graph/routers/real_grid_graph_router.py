from typing import List, Optional
from fastapi import APIRouter

from features.grid_graph.models.grid_graph_types import GridGraph
from features.grid_graph.repositories.grid_graph_repository import GridGraphRepository
from features.grid_graph.services.real_graph_updater_service import RealGraphUpdaterService


grid_graph_base_route = "/api/v1/grid-graphs"

def create_real_grid_graph_router(
    graph_repository: GridGraphRepository,
    graph_updater_service: RealGraphUpdaterService
):
    router = APIRouter()

    @router.get(grid_graph_base_route + "/{grid_id}")
    async def get_graph_by_grid_id(grid_id: int) -> Optional[GridGraph]:
        return graph_repository.find_by_grid_id(grid_id)
    
    @router.get(grid_graph_base_route)
    async def get_all_graphs() -> List[GridGraph]:
        return graph_repository.find_all()
    
    @router.post(grid_graph_base_route)
    async def add_graph(grid_graph: GridGraph):
        graph_repository.add(grid_graph)

    @router.post(grid_graph_base_route + "/bulk")
    async def add_graphs(grid_graphs: List[GridGraph]):
        graph_repository.add_all(grid_graphs)

    @router.put(grid_graph_base_route)
    async def update_graph(grid_graph: GridGraph):
        graph_repository.update(grid_graph)

    @router.delete(grid_graph_base_route + "/{grid_id}")
    async def delete_graph(grid_id: int):
        graph_repository.delete_by_grid_id(grid_id)

    @router.delete(grid_graph_base_route + "/bulk")
    async def delete_all_graphs():
        graph_repository.delete_all()

    
    @router.put(grid_graph_base_route + "/refresh/{grid_id}")
    async def refresh_graph(grid_id: int):
        graph_updater_service.update_grid_graph(grid_id)

    @router.put(grid_graph_base_route + "/refresh")
    async def refresh_all_graphs():
        graph_updater_service.update_all()

    return router