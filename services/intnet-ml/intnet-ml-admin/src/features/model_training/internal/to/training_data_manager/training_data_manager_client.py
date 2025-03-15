
from abc import ABC, abstractmethod
from typing import List, Optional


class TrainingDataManagerClient(ABC):

    @abstractmethod
    async def get_graph_by_grid_id(self, grid_id: int) -> Optional[dict]:
        pass
    
    @abstractmethod
    async def get_records_by_grid_id(self, grid_id: int) -> Optional[List[dict]]:
        pass