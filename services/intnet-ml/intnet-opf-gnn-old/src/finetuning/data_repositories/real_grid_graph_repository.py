
from abc import ABC, abstractmethod
from typing import List, Optional

from core.common.data_types import GridGraph


class RealGridGraphRepository(ABC):
    
    @abstractmethod
    def find(self) -> Optional[GridGraph]:
        pass

    @abstractmethod
    def save(self, grid_graph: GridGraph):
        pass