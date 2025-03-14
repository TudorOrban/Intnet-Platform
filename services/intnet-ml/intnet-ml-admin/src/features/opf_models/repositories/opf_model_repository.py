
from abc import ABC, abstractmethod
from typing import List, Optional

from features.opf_models.models.opf_model import OPFModel


class OPFModelRepository(ABC):

    @abstractmethod
    def find_by_id(self, id: int) -> Optional[OPFModel]:
        pass

    @abstractmethod
    def find_all(self) -> List[OPFModel]:
        pass

    @abstractmethod
    def create(self, opf_model: OPFModel) -> OPFModel:
        pass

    @abstractmethod
    def update(self, opf_model: OPFModel) -> Optional[OPFModel]:
        pass

    @abstractmethod
    def delete_by_id(self, id: int) -> bool:
        pass
