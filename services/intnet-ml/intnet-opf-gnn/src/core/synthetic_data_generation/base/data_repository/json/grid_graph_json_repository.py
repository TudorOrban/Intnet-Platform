
import json
from pathlib import Path
from typing import List, Optional

from core.synthetic_data_generation.common.data_types import GridGraph
from core.synthetic_data_generation.common.graph_data_deserializer import GraphDataDeserializer


class GridGraphJsonRepository:
    def __init__(self, file_path: str = "synthetic_data/grid_graphs.json"):
        self.file_path = Path(file_path)
        self.file_path.parent.mkdir(parents=True, exist_ok=True)


    def find_by_id(self, id: int) -> Optional[GridGraph]:
        grid_graphs = self.find_all()

        for graph in grid_graphs:
            if graph.id == id:
                return graph
            
        return None

    def find_all(self, limit=1000) -> List[GridGraph]:
        if not self.file_path.exists():
            return []

        with open(self.file_path, "r") as f:
            data = json.load(f)

        graphs = []
        for i, grid_graph in enumerate(data):
            if limit != -1 and i > limit:
                break
            
            graphs.append(GraphDataDeserializer.deserialize_grid_graph(grid_graph))

        return graphs

    def save(self, graph: GridGraph):
        graphs = self.find_all()
        graphs.append(graph)

        data = [GraphDataDeserializer.serialize_grid_graph(s) for s in graphs]

        with open(self.file_path, "w") as f:
            json.dump(data, f, indent=4)

    def save_all(self, new_graphs: List[GridGraph]):
        graphs = self.find_all()
        graphs.extend(new_graphs)

        data = [GraphDataDeserializer.serialize_grid_graph(s) for s in graphs]

        with open(self.file_path, "w") as f:
            json.dump(data, f, indent=4)

    def delete_by_id(self, id: int):
        data = self.find_all()
        updated_data = [item for item in data if item.get("id") != id]
        
        data = [GraphDataDeserializer.serialize_grid_graph(s) for s in updated_data]

        with open(self.file_path, "w") as f:
            json.dump(data, f, indent=4)

    def delete_all(self):
        with open(self.file_path, "w") as f:
            json.dump([], f, indent=4)