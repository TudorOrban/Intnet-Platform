
import os
from typing import List, Optional
import httpx

class TrainingDataManagerClient:
    def __init__(self):
        self.base_url = os.getenv("TRAINING_DATA_MANAGER_URL")
        self.client = httpx.AsyncClient(base_url=self.base_url)

    async def get_graph_by_grid_id(self, grid_id: int) -> Optional[dict]:
        try:
            response = await self.client.get(f"/grid-graphs/{grid_id}")
            response.raise_for_status()
            return response.json()
        except httpx.HTTPStatusError:
            return None
        except httpx.RequestError:
            return None
        
    async def get_records_by_grid_id(self, grid_id: int) -> Optional[List[dict]]:
        try:
            response = await self.client.get(f"/dynamic-data-records/grid/{grid_id}")
            response.raise_for_status()
            return response.json()
        except httpx.HTTPStatusError:
            return None
        except httpx.RequestError:
            return None