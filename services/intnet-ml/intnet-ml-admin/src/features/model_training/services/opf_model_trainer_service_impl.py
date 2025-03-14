
from typing import Optional
from features.model_training.models.train_model_request import TrainOPFModelRequest
from features.model_training.services.opf_model_trainer_service import OPFModelTrainerService


class OPFModelTrainerServiceImpl(OPFModelTrainerService):

    def train_opf_model(request: TrainOPFModelRequest) -> Optional[bytes]:
        """Trains a GNN model using given hyperparameters and data from Training Data Manager"""
        