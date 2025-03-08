
from core.internal.out.grid_data.service.grid_data_communicator_service import GridDataCommunicatorService


class RealGraphUpdaterService:

    def __init__(self, grid_data_communicator_service: GridDataCommunicatorService):
        self.grid_data_communicator_service = grid_data_communicator_service

    