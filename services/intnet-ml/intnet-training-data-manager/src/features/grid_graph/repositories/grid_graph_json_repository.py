
import json
from pathlib import Path
import random
from typing import List, Optional

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


    def find_by_grid_id(self, grid_id: int) -> Optional[GridGraph]:
        graphs = self.find_all()
        for graph in graphs:
            if graph.grid_id == grid_id:
                return graph
            
        return None

    def find_all(self, limit=1000) -> List[GridGraph]:
        if not self.file_path.exists():
            return []

        with open(self.file_path, "r") as f:
            data = json.load(f)

        grid_graphs = []
        for i, grid_graph in enumerate(data):
            if limit != -1 and i > limit:
                break
            
            grid_graphs.append(GridGraphJsonMapper.deserialize_grid_graph(grid_graph))

        return grid_graphs
    
    def add(self, grid_graph: GridGraph):
        graphs = self.find_all()
        existing_ids = {g.id for g in graphs}
        grid_graph.id = self._generate_unique_integer_id(existing_ids)

        graphs.append(grid_graph)
        data = [GridGraphJsonMapper.serialize_grid_graph(graph) for graph in graphs]

        with open(self.file_path, "w") as f:
            json.dump(data, f, indent=4)   

    def add_all(self, grid_graphs: List[GridGraph]):
        graphs = self.find_all()
        existing_ids = {g.id for g in graphs}
        for graph in grid_graphs:
            graph.id = self._generate_unique_integer_id(existing_ids)
            graphs.append(graph)
        data = [GridGraphJsonMapper.serialize_grid_graph(graph) for graph in graphs]

        with open(self.file_path, "w") as f:
            json.dump(data, f, indent=4)   
    
    def update(self, grid_graph: GridGraph):
        graphs = self.find_all()
        updated = False
        for i, graph in enumerate(graphs):
            if graph.grid_id == grid_graph.grid_id:
                graphs[i] = grid_graph
                updated = True
                break
        data = [GridGraphJsonMapper.serialize_grid_graph(graph) for graph in graphs]

        if updated:
            with open(self.file_path, "w") as f:
                json.dump(data, f, indent=4)   


    def _generate_unique_integer_id(self, existing_ids: set) -> int:
        while True:
            new_id = random.randint(1, 2147483647)
            if new_id not in existing_ids:
                return new_id
