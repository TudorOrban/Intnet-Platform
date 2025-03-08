
from typing import List
from features.dynamic_data_record.models.record_types import DynamicDataRecord
from features.grid_graph.models.grid_graph_types import GridGraphData


class RecordGeneratorService:

    def generate_records(graph_data: GridGraphData) -> List[DynamicDataRecord]:
        """Generates random Dynamic Data Records with OPF solutions"""
        