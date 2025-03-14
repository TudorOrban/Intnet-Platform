from typing import List, Optional

from features.opf_models.models.dto_mapper import OPFModelDtoMapper
from features.opf_models.models.opf_model_dtos import CreateOPFModelDto, OPFModelSearchDto, UpdateOPFModelDto
from features.opf_models.repositories.opf_model_data_repository import OPFModelDataRepository
from features.opf_models.repositories.opf_model_repository import OPFModelRepository
from features.opf_models.services.opf_model_service import OPFModelService

class OPFModelServiceImpl(OPFModelService):
    def __init__(
        self, 
        opf_model_repository: OPFModelRepository,
        opf_model_data_repository: OPFModelDataRepository
    ):
        self.model_repository = opf_model_repository
        self.model_data_repository = opf_model_data_repository


    def get_model_by_id(self, id: int) -> Optional[OPFModelSearchDto]:
        model = self.model_repository.find_by_id(id)
        if model:
            return OPFModelDtoMapper.map_opf_model_to_search_dto(model)
        return None

    def get_model_data(self, id: int) -> Optional[bytes]:
        return self.model_data_repository.find(id)

    def get_all_models(self) -> List[OPFModelSearchDto]:
        models = self.model_repository.find_all()
        return [OPFModelDtoMapper.map_opf_model_to_search_dto(model) for model in models]

    def create_model(self, model_dto: CreateOPFModelDto, model_data: Optional[bytes]) -> Optional[OPFModelSearchDto]:
        model = OPFModelDtoMapper.map_create_dto_to_opf_model(model_dto)
        created_model = self.model_repository.create(model)
        if not created_model:
            return None
        
        full_dto = OPFModelDtoMapper.map_opf_model_to_search_dto(created_model)
        
        saved_data = self.model_data_repository.save(model_id=full_dto.id, model_data=model_data)
        if not saved_data:
            # Roll back model creation
            self.delete_model_by_id(full_dto.id)
            return None
        
        return full_dto

    def update_model(self, model_dto: UpdateOPFModelDto, model_data: Optional[bytes]) -> Optional[OPFModelSearchDto]:
        existing_model = self.model_repository.find_by_id(model_dto.id)
        if not existing_model:
            return None

        existing_model.name = model_dto.name if model_dto.name is not None else existing_model.name
        existing_model.storage_path = model_dto.storage_path if model_dto.storage_path is not None else existing_model.storage_path
        existing_model.description = model_dto.description if model_dto.description is not None else existing_model.description
        existing_model.version = model_dto.version if model_dto.version is not None else existing_model.version

        updated_model = self.model_repository.update(existing_model)
        if not updated_model:
            return None
        
        full_dto = OPFModelDtoMapper.map_opf_model_to_search_dto(updated_model)
        if not model_data:
            return full_dto
        
        self.model_data_repository.update(model_id=full_dto.id, model_data=model_data)
        
        return full_dto


    def delete_model_by_id(self, id: int) -> bool:
        is_success = self.model_repository.delete_by_id(id)
        if not is_success:
            return False
        
        return self.model_data_repository.delete(id)