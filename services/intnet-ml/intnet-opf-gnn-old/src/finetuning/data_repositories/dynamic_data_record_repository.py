
from abc import ABC, abstractmethod
from typing import List

from finetuning.common.data_types import DynamicDataRecord


class DynamicDataRecordRepository(ABC):
    
    @abstractmethod
    def find_all(self) -> List[DynamicDataRecord]:
        pass

    @abstractmethod
    def save_all(self, records: List[DynamicDataRecord]):
        pass