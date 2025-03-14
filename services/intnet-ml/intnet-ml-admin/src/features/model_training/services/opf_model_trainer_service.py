
from abc import ABC, abstractmethod
from typing import Optional

from features.model_training.models.train_model_request import TrainOPFModelRequest


class OPFModelTrainerService(ABC):

    @abstractmethod
    def train_opf_model(request: TrainOPFModelRequest) -> Optional[bytes]:
        pass