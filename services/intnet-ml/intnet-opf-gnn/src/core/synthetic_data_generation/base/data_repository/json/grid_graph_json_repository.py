
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
        if not self.file_path.exists():
            return None
        
        with open(self.file_path, "r") as f:
            data = json.load(f)

        return GraphDataDeserializer.deserialize_grid_graph(data)

    def find_all(self, limit=1000) -> List[GridGraph]:
        if not self.file_path.exists():
            return []

        with open(self.file_path, "r") as f:
            data = json.load(f)

        samples = []
        for i, sample_data in enumerate(data):
            if limit != -1 and i > limit:
                break
            
            samples.append(GraphDataDeserializer.deserialize_grid_graph(sample_data))

        return samples

    def save(self, sample: GridGraph):
        samples = self.find_all()
        samples.append(sample)

        data = [GraphDataDeserializer.serialize_grid_graph(s) for s in samples]

        with open(self.file_path, "w") as f:
            json.dump(data, f, indent=4)

    def save_all(self, new_samples: List[GridGraph]):
        samples = self.find_all()
        samples.extend(new_samples)

        data = [GraphDataDeserializer.serialize_grid_graph(s) for s in samples]

        with open(self.file_path, "w") as f:
            json.dump(data, f, indent=4)