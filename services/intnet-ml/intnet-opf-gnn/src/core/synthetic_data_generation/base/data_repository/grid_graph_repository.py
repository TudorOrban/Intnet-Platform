
from abc import ABC, abstractmethod
from typing import List, Optional

from core.synthetic_data_generation.common.data_types import GridGraph


class GridGraphRepository(ABC):
    
    @abstractmethod
    def find_by_id(self, graph_id: int) -> Optional[GridGraph]:
        pass

    @abstractmethod
    def find_all(self, limit=1000) -> List[GridGraph]:
        pass
    
    @abstractmethod
    def save(self, grid_graph: GridGraph):
        pass

    @abstractmethod
    def save_all(self, grid_graphs: List[GridGraph]):
        pass

    @abstractmethod
    def delete_by_id(self, graph_id: int):
        pass