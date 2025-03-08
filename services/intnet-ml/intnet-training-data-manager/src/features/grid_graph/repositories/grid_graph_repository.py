
from abc import ABC, abstractmethod
from typing import List, Optional

from features.grid_graph.models.grid_graph_types import GridGraph


class GridGraphRepository(ABC):
    
    @abstractmethod
    def find_by_grid_id(self, grid_id: int) -> Optional[GridGraph]:
        pass

    @abstractmethod
    def find_all(self) -> List[GridGraph]:
        pass

    @abstractmethod
    def add(self, grid_graph: GridGraph):
        pass

    @abstractmethod
    def add_all(self, grid_graphs: List[GridGraph]):
        pass

    @abstractmethod
    def update(self, grid_graph: GridGraph):
        pass

    @abstractmethod
    def delete_by_grid_id(self, grid_id: int):
        pass

    @abstractmethod
    def delete_all(self):
        pass