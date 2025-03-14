from typing import Annotated, List, Optional
from fastapi import APIRouter, Depends, File, Form, Response, UploadFile
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

    @router.get("/{model_id}/data")
    @inject
    async def get_model_data(
        model_id: int,
        model_service: OPFModelService = Depends(get_model_service),
    ):
        model_data = model_service.get_model_data(model_id)
        if not model_data:
            return Response(status_code=404)
        return Response(content=model_data, media_type="application/octet-stream")

    @router.get("", response_model=List[OPFModelSearchDto])
    @inject
    async def get_all_models(
        model_service: OPFModelService = Depends(get_model_service),
    ):
        return model_service.get_all_models()

    @router.post("", response_model=OPFModelSearchDto)
    @inject
    async def create_model(
        data: Annotated[UploadFile, File()],
        name: str = Form(...),
        storage_path: Optional[str] = Form(None),
        description: Optional[str] = Form(None),
        version: Optional[str] = Form(None),
        model_service: OPFModelService = Depends(get_model_service),
    ):
        model_dto = CreateOPFModelDto(
            name=name, storage_path=storage_path, description=description, version=version
        )
        model_data = await data.read()
        return model_service.create_model(model_dto, model_data)

    @router.put("/{id}", response_model=OPFModelSearchDto)
    @inject
    async def update_model(
        data: Annotated[UploadFile, File()],
        id: int,
        name: str = Form(...),
        storage_path: Optional[str] = Form(None),
        description: Optional[str] = Form(None),
        version: Optional[str] = Form(None),
        model_service: OPFModelService = Depends(get_model_service),
    ):
        model_dto = UpdateOPFModelDto(
            id=id, name=name, storage_path=storage_path, description=description, version=version
        )
        model_data = await data.read()
        return model_service.update_model(model_dto, model_data)

    @router.delete("/{model_id}")
    @inject
    async def delete_model(
        model_id: int,
        model_service: OPFModelService = Depends(get_model_service),
    ):
        return model_service.delete_model_by_id(model_id)

    return router