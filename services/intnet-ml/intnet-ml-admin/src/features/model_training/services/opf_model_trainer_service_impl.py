
from typing import Optional
from features.model_training.internal.to.training_data_manager.training_data_manager_client import TrainingDataManagerClient
from features.model_training.models.train_model_request import TrainOPFModelRequest
from features.model_training.services.opf_model_trainer_service import OPFModelTrainerService


class OPFModelTrainerServiceImpl(OPFModelTrainerService):
    def __init__(
        self,
        training_data_manager_client: TrainingDataManagerClient
    ):
        self.training_data_manager_client = training_data_manager_client

    def train_opf_model(self, request: TrainOPFModelRequest) -> Optional[bytes]:
        """Trains a GNN model using given hyperparameters and data from Training Data Manager"""
        grid_graph = self.training_data_manager_client.get_graph_by_grid_id(request.grid_id)
        grid_records = self.training_data_manager_client.get_records_by_grid_id(request.grid_id)