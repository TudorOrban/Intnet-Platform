import structlog

from typing import List
from core.synthetic_data_generation.base.data_generators.solution_generator.opf_solution_generator import generate_opf_sample
from core.synthetic_data_generation.common.data_types import GridGraphData
from core.synthetic_data_generation.base.data_generators.random_data_generator.random_dynamic_data_generator import generate_random_dynamic_data
from core.synthetic_data_generation.base.data_generators.random_data_generator.random_grid_topology_generator import generate_random_topology
from core.synthetic_data_generation.base.data_generators.random_data_generator.random_static_data_generator import generate_random_static_data

logger = structlog.get_logger(__name__)

def generate_flat_samples(topologies=3, specifications=5, records=10) -> List[GridGraphData]:
    """Generates synthetic data samples (flat version)"""

    samples: List[GridGraphData] = []
    logger.info("Starting flat sample generation", topologies=topologies, specifications=specifications, records=records)

    for topology_index in range(topologies):
        logger.info(f"Generating topology {topology_index + 1}/{topologies}")
        graph_topology = generate_random_topology(num_buses=12, num_generators=2, num_loads=5, edge_density=0.3)
        
        for specification_index in range(specifications):
            logger.info(f"Generating specification {specification_index + 1}/{specifications}")
            graph_specification = generate_random_static_data(graph_data=graph_topology)

            for record_index in range(records):
                logger.info(f"Generating record {record_index + 1}/{records}")
                graph_data = generate_random_dynamic_data(graph_data=graph_specification)

                graph_data, has_converged = generate_opf_sample(graph_data)
                if has_converged:
                    samples.append(graph_data)
        
    logger.info("Finished flat sample generation", num_samples=len(samples))
    return samples
