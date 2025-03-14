
from abc import ABC, abstractmethod
from typing import List, Optional

from features.opf_models.models.opf_model_dtos import CreateOPFModelDto, OPFModelSearchDto, UpdateOPFModelDto


class OPFModelService(ABC):

    @abstractmethod
    def get_model_by_id(self, id: int) -> Optional[OPFModelSearchDto]:
        pass

    @abstractmethod
    def get_model_data(self, id: int) -> Optional[bytes]:
        pass

    @abstractmethod
    def get_all_models(self) -> List[OPFModelSearchDto]:
        pass

    @abstractmethod
    def create_model(self, model_dto: CreateOPFModelDto, model_data: Optional[bytes]) -> Optional[OPFModelSearchDto]:
        pass

    @abstractmethod
    def update_model(self, model_dto: UpdateOPFModelDto, model_data: Optional[bytes]) -> Optional[OPFModelSearchDto]:
        pass

    @abstractmethod
    def delete_model_by_id(self, id: int) -> bool:
        pass