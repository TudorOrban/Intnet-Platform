
from datetime import datetime
from typing import List
from core.data_repository.grid_graph_repository_creator import create_grid_graph_repository
from core.common.data_types import GridGraph, GridGraphData


class GridGraphService:
    def save_generated_samples(self, samples: List[GridGraphData]):
        graph_repository = create_grid_graph_repository()  
              
        grid_graphs: List[GridGraph] = []
        for sample in samples:
            grid_graph = GridGraph(id=0, created_at=datetime.now(), graph_data=sample)
            grid_graphs.append(grid_graph)
        
        graph_repository.save_all(grid_graphs)