from typing import List, Optional
from fastapi import APIRouter, Query

from features.data_generation.topology.types import TopologySpecifications
from features.grid_graph.models.grid_graph_types import GridGraph
from features.grid_graph.repositories.grid_graph_repository import GridGraphRepository
from features.grid_graph.services.synthetic_graph_generator_service import SyntheticGraphGeneratorService


grid_graph_base_route = "/api/v1/synthetic-grid-graphs"

def create_synthetic_graph_router(
    synthetic_graph_repository: GridGraphRepository,
    graph_generator_service: SyntheticGraphGeneratorService
):
    router = APIRouter()

    # CRUD ops
    @router.get(grid_graph_base_route + "/{grid_id}")
    async def get_synthetic_graph_by_grid_id(grid_id: int) -> Optional[GridGraph]:
        return synthetic_graph_repository.find_by_grid_id(grid_id)
    
    @router.get(grid_graph_base_route)
    async def get_all_synthetic_graphs() -> List[GridGraph]:
        return synthetic_graph_repository.find_all()
    
    @router.post(grid_graph_base_route)
    async def add_synthetic_graph(grid_graph: GridGraph):
        synthetic_graph_repository.add(grid_graph)

    @router.post(grid_graph_base_route + "/bulk")
    async def add_synthetic_graphs(grid_graphs: List[GridGraph]):
        synthetic_graph_repository.add_all(grid_graphs)

    @router.post(grid_graph_base_route)
    async def update_synthetic_graph(grid_graph: GridGraph):
        synthetic_graph_repository.update(grid_graph)

    @router.delete(grid_graph_base_route + "/{grid_id}")
    async def delete_synthetic_graph(grid_id: int):
        synthetic_graph_repository.delete_by_grid_id(grid_id)

    @router.delete(grid_graph_base_route + "/bulk")
    async def delete_all_synthetic_graphs():
        synthetic_graph_repository.delete_all()
        
    # Data generation
    @router.post(grid_graph_base_route + "/generate/{grid_id}", response_model=GridGraph)
    async def generate_synthetic_graph(
        specifications: TopologySpecifications, 
        grid_id: int,
        try_limit: int = Query(20)
    ):
        return graph_generator_service.generate_synthetic_graph(specifications, grid_id, try_limit)

    return router