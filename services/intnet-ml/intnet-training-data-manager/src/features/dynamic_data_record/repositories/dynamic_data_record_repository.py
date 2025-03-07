
from abc import ABC, abstractmethod
from typing import List, Optional

from features.dynamic_data_record.models.record_types import DynamicDataRecord
from features.grid_graph.models.grid_graph_types import GridGraph


class DynamicDataRecordRepository(ABC):
    
    @abstractmethod
    def find_by_id(self, id: int) -> Optional[DynamicDataRecord]:
        pass

    @abstractmethod
    def find_all(self, limit: int=1000) -> List[DynamicDataRecord]:
        pass

    @abstractmethod
    def save(self, record: DynamicDataRecord):
        pass

    @abstractmethod
    def save_all(self, records: List[DynamicDataRecord]):
        pass

    @abstractmethod
    def delete_by_id(self, id: int):
        pass