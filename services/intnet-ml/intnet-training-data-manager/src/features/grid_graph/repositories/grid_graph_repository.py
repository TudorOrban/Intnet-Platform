
from abc import ABC, abstractmethod
from typing import Optional

from features.grid_graph.models.grid_graph_types import GridGraph


class GridGraphRepository(ABC):
    
    @abstractmethod
    def find(self) -> Optional[GridGraph]:
        pass

    @abstractmethod
    def save(self, grid_graph: GridGraph):
        pass