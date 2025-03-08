
from fastapi import APIRouter

from features.data_generation.topology.types import TopologySpecifications
from features.grid_graph.services.synthetic_graph_generator_service import SyntheticGraphGeneratorService


grid_graph_base_route = "/api/v1/synthetic-grid-graph"

def create_synthetic_graph_router(graph_generator_service: SyntheticGraphGeneratorService):
    router = APIRouter()

    @router.post(grid_graph_base_route)
    async def generate_synthetic_graph(specifications: TopologySpecifications):
        return graph_generator_service.generate_synthetic_graph(specifications)

    return router