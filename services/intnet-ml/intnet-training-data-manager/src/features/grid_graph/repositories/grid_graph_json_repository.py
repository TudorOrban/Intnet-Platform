
import json
from pathlib import Path
from typing import Optional

from features.grid_graph.models.grid_graph_types import GridGraph
from features.grid_graph.utils.grid_graph_json_mapper import GridGraphJsonMapper
from shared.enums import GraphType

"""
Repository for Grid Graph using JSON storage for fast iteration in model development
"""
class GridGraphJsonRepository:
    def __init__(self, graph_type: GraphType):
        file_path_str = "training_data_json_db/synthetic_data/grid_graph.json" if graph_type == GraphType.SYNTHETIC else "training_data_json_db/real_data/grid_graph.json"
        self.file_path = Path(file_path_str)
        self.file_path.parent.mkdir(parents=True, exist_ok=True)


    def find(self) -> Optional[GridGraph]:
        if not self.file_path.exists():
            return None
        
        with open(self.file_path, "r") as f:
            data = json.load(f)
            
        return GridGraphJsonMapper.deserialize_grid_graph(data)
    
    def save(self, grid_graph: GridGraph):
        data = GridGraphJsonMapper.serialize_grid_graph(grid_graph)

        with open(self.file_path, "w") as f:
            json.dump(data, f, indent=4)