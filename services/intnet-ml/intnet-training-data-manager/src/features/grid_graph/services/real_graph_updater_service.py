
from core.internal.out.grid_data.service.grid_data_communicator_service import GridDataCommunicatorService
from features.grid_graph.repositories.grid_graph_repository import GridGraphRepository


class RealGraphUpdaterService:

    def __init__(
        self, 
        real_grid_graph_repository: GridGraphRepository,
        grid_data_communicator_service: GridDataCommunicatorService
    ):
        self.graph_repository = real_grid_graph_repository
        self.grid_data_communicator_service = grid_data_communicator_service

    def update_grid_graph(self, grid_id: int) -> bool:
        graph = self.grid_data_communicator_service.get_grid_graph(grid_id)
        if graph is None:
            return False

        self.graph_repository.update(graph)
        return True
    
    def update_all(self) -> bool:
        graphs = self.graph_repository.find_all()
        grid_ids = [graph.id for graph in graphs]
        
        is_success = False
        for grid_id in grid_ids:
            is_success = self.update_grid_graph(grid_id)

        return is_success