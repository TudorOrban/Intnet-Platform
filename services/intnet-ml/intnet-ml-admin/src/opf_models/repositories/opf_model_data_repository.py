
from abc import ABC, abstractmethod


class OpfModelDataRepository(ABC):

    @abstractmethod
    def load_model(self, model_name: str):
        pass