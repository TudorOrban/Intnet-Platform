from typing import List, Optional

from features.opf_models.models.dto_mapper import OPFModelDtoMapper
from features.opf_models.models.opf_model import OPFModel
from features.opf_models.models.opf_model_dtos import CreateOPFModelDto, OPFModelSearchDto, UpdateOPFModelDto
from features.opf_models.repositories.opf_model_repository import OPFModelRepository

class OPFModelServiceImpl:
    def __init__(self, opf_model_repository: OPFModelRepository):
        self.repository = opf_model_repository


    def get_model_by_id(self, id: int) -> Optional[OPFModelSearchDto]:
        model = self.repository.find_by_id(id)
        if model:
            return OPFModelDtoMapper.map_opf_model_to_dto(model)
        return None

    def get_all_models(self) -> List[OPFModelSearchDto]:
        models = self.repository.find_all()
        return [OPFModelDtoMapper.map_opf_model_to_dto(model) for model in models]

    def create_model(self, model_dto: CreateOPFModelDto) -> Optional[OPFModelSearchDto]:
        model = OPFModelDtoMapper.map_dto_to_opf_model(model_dto)
        created_model = self.repository.create(model)
        if created_model:
            return OPFModelDtoMapper.map_opf_model_to_dto(created_model)
        return None

    def update_model(self, model_dto: UpdateOPFModelDto) -> Optional[OPFModelSearchDto]:
        existing_model = self.repository.find_by_id(model_dto.id)
        if not existing_model:
            return None

        existing_model.name = model_dto.name if model_dto.name is not None else existing_model.name
        existing_model.storage_path = model_dto.storage_path if model_dto.storage_path is not None else existing_model.storage_path
        existing_model.description = model_dto.description if model_dto.description is not None else existing_model.description
        existing_model.version = model_dto.version if model_dto.version is not None else existing_model.version

        updated_model = self.repository.update(existing_model)

        if updated_model:
            return OPFModelDtoMapper.map_opf_model_to_dto(updated_model)
        return None

    def delete_model_by_id(self, id: int) -> bool:
        return self.repository.delete_by_id(id)