
from enum import Enum
from typing import Optional
from pydantic import BaseModel


class OPFModelTrainingHyperparameters:
    learning_rate: float

class TrainJobType(str, Enum):
    PRODUCTION = "PRODUCTION"
    DEVELOPMENT = "DEVELOPMENT"
    EXPERIMENT = "EXPERIMENT"

class TrainOPFModelRequest(BaseModel):
    grid_id: int
    hyperparameters: OPFModelTrainingHyperparameters
    job_type: TrainJobType
    experiment_name: Optional[str] = None