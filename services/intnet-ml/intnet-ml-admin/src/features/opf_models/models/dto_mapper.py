
from features.opf_models.models.opf_model import OPFModel
from features.opf_models.models.opf_model_dtos import CreateOPFModelDto, OPFModelSearchDto


class OPFModelDtoMapper:

    @staticmethod
    def map_opf_model_to_search_dto(model: OPFModel) -> OPFModelSearchDto:
        return OPFModelSearchDto(
            id=model.id,
            name=model.name,
            created_at=model.created_at,
            storage_path=model.storage_path,
            description=model.description,
            version=model.version,
        )
    
    @staticmethod
    def map_create_dto_to_opf_model(model_dto: CreateOPFModelDto) -> OPFModel:
        return OPFModel(
            name=model_dto.name,
            storage_path=model_dto.storage_path,
            description=model_dto.description,
            version=model_dto.version,
        )