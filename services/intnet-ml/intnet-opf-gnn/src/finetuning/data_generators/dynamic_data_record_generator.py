

from typing import List
import structlog

from core.common.data_types import GridGraphData
from core.data_generators.random_data_generator.random_dynamic_data_generator import generate_random_dynamic_data
from core.data_generators.random_data_generator.realistic.realistic_grid_dynamic_data_generator import generate_realistic_dynamic_data
from core.data_generators.solution_generator.opf_solution_generator import generate_opf_sample
from finetuning.common.data_types import DynamicDataRecord
from finetuning.common.dynamic_data_record_manager import extract_dynamic_data_record

logger = structlog.get_logger(__name__)

def generate_dynamic_data_records(graph_specification: GridGraphData, record_count=100) -> List[DynamicDataRecord]:
    """Generates a set of records of random dynamic data for a grid graph with specified topology and static data"""

    records: List[DynamicDataRecord] = []

    for record in range(record_count):
        graph_data = generate_random_dynamic_data(graph_specification)

        graph_data, has_converged = generate_opf_sample(graph_data)
        if has_converged:
            record = extract_dynamic_data_record(graph_data)
            records.append(record)

    return records

def generate_realistic_dynamic_data_records(graph_specification: GridGraphData, record_count=100) -> List[DynamicDataRecord]:
    """Generates a set of records of realistic dynamic data for a grid graph with specified topology and static data"""

    logger.info("Starting generation of dynamic data records.")
    records: List[DynamicDataRecord] = []

    for record_number in range(record_count):
        logger.info(f"Progress: {record_number} / {record_count}")
        graph_data = generate_realistic_dynamic_data(graph_specification)

        graph_data, has_converged = generate_opf_sample(graph_data)
        if has_converged:
            record = extract_dynamic_data_record(graph_data)
            records.append(record)

    logger.info("Finishing generation of dynamic data records")
    return records