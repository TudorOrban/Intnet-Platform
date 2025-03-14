from typing import List
from fastapi import APIRouter, Depends
from dependency_injector.wiring import inject

from core.config.initialization.dependency_container import Container
from features.opf_models.models.opf_model_dtos import CreateOPFModelDto, OPFModelSearchDto, UpdateOPFModelDto
from features.opf_models.services.opf_model_service import OPFModelService

def create_opf_model_router(container: Container):
    router = APIRouter(prefix="/api/v1/opf-models", tags=["OPF Models"])

    def get_model_service(container: Container = Depends(lambda: container)):
        return container.opf_model_service()

    @router.get("/{model_id}", response_model=OPFModelSearchDto)
    @inject
    async def get_model(
        model_id: int,
        model_service: OPFModelService = Depends(get_model_service),
    ):
        return model_service.get_model_by_id(model_id)

    @router.get("", response_model=List[OPFModelSearchDto])
    @inject
    async def get_all_models(
        model_service: OPFModelService = Depends(get_model_service),
    ):
        return model_service.get_all_models()

    @router.post("", response_model=OPFModelSearchDto)
    @inject
    async def create_model(
        model_dto: CreateOPFModelDto,
        model_service: OPFModelService = Depends(get_model_service),
    ):
        return model_service.create_model(model_dto)

    @router.put("", response_model=OPFModelSearchDto)
    @inject
    async def update_model(
        model_dto: UpdateOPFModelDto,
        model_service: OPFModelService = Depends(get_model_service),
    ):
        return model_service.update_model(model_dto)

    @router.delete("/{model_id}")
    @inject
    async def delete_model(
        model_id: int,
        model_service: OPFModelService = Depends(get_model_service),
    ):
        return model_service.delete_model_by_id(model_id)

    return router