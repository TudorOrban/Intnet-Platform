
from fastapi import APIRouter

from features.data_generation.topology.types import TopologySpecifications
from features.grid_graph.models.grid_graph_types import GridGraph
from features.grid_graph.repositories.grid_graph_repository import GridGraphRepository
from features.grid_graph.services.synthetic_graph_generator_service import SyntheticGraphGeneratorService


grid_graph_base_route = "/api/v1/synthetic-grid-graph"

def create_synthetic_graph_router(
    synthetic_graph_repository: GridGraphRepository,
    graph_generator_service: SyntheticGraphGeneratorService
):
    router = APIRouter()

    # CRUD ops
    @router.get(grid_graph_base_route)
    async def get_grid_graph():
        grid_graph = synthetic_graph_repository.find()
        return grid_graph

    @router.post(grid_graph_base_route)
    async def update_grid_graph(grid_graph: GridGraph):
        synthetic_graph_repository.save(grid_graph)

    @router.delete(grid_graph_base_route)
    async def delete_grid_graph():
        synthetic_graph_repository.delete()
        
    # Data generation
    @router.post(grid_graph_base_route + "/generate", response_model=GridGraph)
    async def generate_synthetic_graph(specifications: TopologySpecifications):
        return graph_generator_service.generate_synthetic_graph(specifications)

    return router