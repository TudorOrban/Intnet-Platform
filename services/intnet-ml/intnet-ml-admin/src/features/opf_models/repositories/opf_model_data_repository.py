
from abc import ABC, abstractmethod
from typing import Optional


class OPFModelDataRepository(ABC):

    @abstractmethod
    def find(self, model_id: int) -> Optional[bytes]:
        pass

    @abstractmethod
    def save(self, model_id: int, model_data: bytes) -> Optional[bytes]:
        pass

    @abstractmethod
    def update(self, model_id: int, model_data: bytes) -> Optional[bytes]:
        pass

    @abstractmethod
    def delete(self, model_id: int) -> bool:
        pass
