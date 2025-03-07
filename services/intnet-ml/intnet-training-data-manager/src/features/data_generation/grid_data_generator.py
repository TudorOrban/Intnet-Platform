
from abc import ABC, abstractmethod

from features.grid_graph.models.grid_graph_types import GridGraphData


class GridDataGenerator(ABC):

    @abstractmethod
    def generate_random_topology(num_buses=10, num_generators=3, num_loads=4, der_density=0.2, storage_unit_density=0.1, edge_density=0.1) -> GridGraphData:
        pass

    @abstractmethod
    def generate_random_static_data(graph_data: GridGraphData):
        pass

    @abstractmethod
    def generate_random_dynamic_data(graph_data: GridGraphData):
        pass