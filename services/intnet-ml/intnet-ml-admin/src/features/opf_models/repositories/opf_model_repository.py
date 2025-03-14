
from abc import ABC, abstractmethod


class OpfModelRepository(ABC):

    @abstractmethod
    def load_model(self, model_name: str):
        pass