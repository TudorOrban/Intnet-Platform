from typing import List
from fastapi import Depends, FastAPI
from dependency_injector.wiring import inject, Provide

from core.config.db.database_connection import initialize_database
from core.config.initialization.app_initializer import initialize_app
from core.config.initialization.dependency_container import Container
from features.opf_models.models.opf_model_dtos import CreateOPFModelDto, OPFModelSearchDto, UpdateOPFModelDto
from features.opf_models.services.opf_model_service import OPFModelService
from features.opf_models.services.opf_model_service_impl import OPFModelServiceImpl

initialize_app()
initialize_database()

app = FastAPI()
container = Container()
app.container = container

print(f"__name__ is: {__name__}")
container.wire(modules=["__main__"])
test_service = container.opf_model_service()
print(f"Test Service Instance: {test_service}")
print(f"Test Service Type: {type(test_service)}")

def get_model_service(container: Container = Depends(lambda: app.container)):
    return container.opf_model_service()

@app.get("/")
async def root():
    return {"message": "Intnet ML Admin Service is running"}

@app.get("/models/{model_id}", response_model=OPFModelSearchDto)
@inject
async def get_model(model_id: int, model_service: OPFModelService = Depends(Provide[container.opf_model_service])):
    return model_service.get_model_by_id(model_id)

@app.get("/models/", response_model=List[OPFModelSearchDto])
@inject
async def get_all_models(model_service: OPFModelService = Depends(Provide[container.opf_model_service])):
    return model_service.get_all_models()

@app.post("/models/", response_model=OPFModelSearchDto)
@inject
async def create_model(
    model_dto: CreateOPFModelDto, 
    model_service: OPFModelService = Depends(get_model_service),
):
    print(model_service)
    print(type(model_service))
    return model_service.create_model(model_dto)

@app.put("/models/", response_model=OPFModelSearchDto)
@inject
async def update_model(model_dto: UpdateOPFModelDto, model_service: OPFModelService = Depends(Provide[container.opf_model_service])):
    return model_service.update_model(model_dto)

@app.delete("/models/{model_id}")
@inject
async def delete_model(model_id: int, model_service: OPFModelService = Depends(Provide[container.opf_model_service])):
    return model_service.delete_model_by_id(model_id)