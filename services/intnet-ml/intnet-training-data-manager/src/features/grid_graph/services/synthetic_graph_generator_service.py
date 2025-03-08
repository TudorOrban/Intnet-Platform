
from datetime import datetime
import structlog

from features.data_generation.dynamic_data.random_grid_dynamic_data_generator import generate_random_dynamic_data
from features.data_generation.solution.opf_solution_generator import generate_opf_solution
from features.data_generation.static_data.random_grid_static_data_generator import generate_random_static_data
from features.data_generation.topology.random_grid_topology_generator import generate_random_grid_topology
from features.data_generation.topology.types import TopologySpecifications
from features.grid_graph.models.grid_graph_types import GridGraph
from features.grid_graph.repositories.grid_graph_repository import GridGraphRepository


logger = structlog.get_logger(__name__)

class SyntheticGraphGeneratorService:

    def __init__(self, graph_repository: GridGraphRepository):
        self.graph_repository = graph_repository

    def generate_synthetic_graph(self, specifications: TopologySpecifications, try_limit=10) -> GridGraph:
        """Attempts to generate a synthetic graph of specified size"""
        
        logger.info("Starting generation of synthetic grid graph.")
    
        for i in range(try_limit):
            logger.info(f"Try: {i} / {try_limit}")
            graph_topology = generate_random_grid_topology(specifications)
            graph_specification = generate_random_static_data(graph_topology)
            graph_data = generate_random_dynamic_data(graph_specification)

            graph_data, has_converged = generate_opf_solution(graph_data)
            if has_converged:
                grid_graph = GridGraph(id=0, created_at=datetime.now(), graph_data=graph_data)
                self.graph_repository.save(grid_graph)
                logger.info("Finished generation of synthetic grid graph.")
                return grid_graph

        logger.info("Failed to generate a synhtetic grid graph: OPF did not converge.")
