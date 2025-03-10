from datetime import datetime
import structlog
from typing import List

from features.data_generation.dynamic_data.random_grid_dynamic_data_generator import generate_random_dynamic_data
from features.data_generation.solution.opf_solution_generator import generate_opf_solution
from features.dynamic_data_record.models.record_types import DynamicDataRecord
from features.dynamic_data_record.repositories.dynamic_data_record_repository import DynamicDataRecordRepository
from features.dynamic_data_record.utils.dynamic_data_record_extractor import extract_dynamic_data_record
from features.grid_graph.models.grid_graph_types import GridGraphData
from features.grid_graph.repositories.grid_graph_repository import GridGraphRepository


logger = structlog.get_logger(__name__)

class RecordGeneratorService:

    def __init__(
        self, 
        graph_repository: GridGraphRepository,
        record_repository: DynamicDataRecordRepository
    ):
        self.graph_repository = graph_repository
        self.record_repository = record_repository

    def generate_and_save_synthetic_records(self, grid_id: int, count=10, try_limit=100) -> List[DynamicDataRecord]:
        graph = self.graph_repository.find_by_grid_id(grid_id)
        if graph is None:
            return []
        
        synthetic_records = self.generate_synthetic_records(graph_data=graph.graph_data, grid_id=grid_id, count=count, try_limit=try_limit)
        self.record_repository.save_all(synthetic_records)

        return synthetic_records

    def generate_synthetic_records(self, graph_data: GridGraphData, grid_id: int, count=10, try_limit=100) -> List[DynamicDataRecord]:
        """Generates random synthetic Dynamic Data Records with OPF solutions"""
        
        logger.info("Starting generation of dynamic data records.")
    
        records: List[DynamicDataRecord] = []
        convergent_count = 0

        for i in range(try_limit):
            if convergent_count > count:
                break

            logger.info(f"Progress: {convergent_count} / {count}, Try: {i} / {try_limit}")
            graph_data = generate_random_dynamic_data(graph_data)
            
            graph_data, has_converged = generate_opf_solution(graph_data)
            if has_converged:
                record_data = extract_dynamic_data_record(graph_data)
                record = DynamicDataRecord(id=0, grid_id=grid_id, created_at=datetime.now(), record_data=record_data)
                records.append(record)
                convergent_count += 1

        logger.info("Finished generation of dynamic data records.")

        return records