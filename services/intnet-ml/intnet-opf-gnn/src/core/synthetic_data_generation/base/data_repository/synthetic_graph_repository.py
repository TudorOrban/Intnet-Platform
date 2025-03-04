import datetime
import json
from typing import List
from pathlib import Path

from core.synthetic_data_generation.common.data_types import GridGraph, GridGraphData, Bus, Edge, Generator, Load, BusState, EdgeState, GeneratorState, LoadState, BusType
from core.synthetic_data_generation.common.graph_data_deserializer import GraphDataDeserializer

class SyntheticGraphRepository:
    def __init__(self, file_path: str = "synthetic_data/base_graph_data.json"):
        self.file_path = Path(file_path)
        self.file_path.parent.mkdir(parents=True, exist_ok=True)

    def read_graphs(self) -> List[GridGraph]:
        if not self.file_path.exists():
            return []

        with open(self.file_path, "r") as f:
            data = json.load(f)


        graphs = []
        for graph in data:
            id = graph["id"]
            created_at = graph["created_at"]
            graph_data = GraphDataDeserializer.deserialize_graph_data(graph["graph_data"])
            
            graphs.append(GridGraph(id=id, created_at=created_at, graph_data=graph_data))

        return graphs

    def add_graph(self, graph_data: GridGraphData):
        graphs = self.read_graphs()

        max_id = max((g.id for g in graphs), default=0)

        new_graph = GridGraph(id=max_id + 1, created_at=datetime.datetime.now(), graph_data=graph_data)

        graphs.append(new_graph)

        data = [GraphDataDeserializer.serialize_graph(g) for g in graphs]

        with open(self.file_path, "w") as f:
            json.dump(data, f, indent=4)

    