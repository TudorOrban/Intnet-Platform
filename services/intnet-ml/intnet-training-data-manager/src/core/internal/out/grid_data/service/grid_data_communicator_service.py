import os
import requests
from typing import Optional

import structlog

from features.grid_graph.models.grid_graph_types import GridGraph
from features.grid_graph.utils.grid_graph_json_mapper import GridGraphJsonMapper


logger = structlog.get_logger(__name__)

class GridDataCommunicatorService:

    def __init__(self):
        service_url = os.getenv("GRID_DATA_SERVICE_URL", "std-release-intnet-grid-data")
        self.base_url = f"{service_url}/api/v1/internal/grid-graphs"

    def get_grid_graph(self, grid_id: int) -> Optional[GridGraph]:
        url = f"{self.base_url}/{grid_id}"
        try:
            response = requests.get(url)
            response.raise_for_status()
            data = response.json()
            return GridGraphJsonMapper.deserialize_grid_graph(data)
        except requests.exceptions.RequestException as e:
            logger.error(f"Error communicating with Grid Data Service: {e}")
            return None
        except ValueError:
            logger.error(f"Invalid JSON response from Grid Data Service: {e}")
            return None