
from dependency_injector.wiring import inject
from fastapi import APIRouter, Depends
from core.config.initialization.dependency_container import Container
from features.model_training.models.train_model_request import TrainOPFModelRequest
from features.model_training.services.opf_model_trainer_service import OPFModelTrainerService


def create_opf_model_training_router(container: Container):
    router = APIRouter(prefix="/api/v1/opf-model-training", tags=["OPF Model Training"])

    def get_trainer_service(container: Container = Depends(lambda: container)):
        return container.opf_model_trainer_service()

    @router.post("")
    @inject
    async def train_model(
        request: TrainOPFModelRequest,
        trainer_service: OPFModelTrainerService = Depends(get_trainer_service),
    ):
        return trainer_service.train_opf_model(request)

    return router