
import os
from typing import Optional
from features.opf_models.repositories.opf_model_data_repository import OPFModelDataRepository


class LocalOPFModelDataRepositoryImpl(OPFModelDataRepository):
    def __init__(self, storage_dir: str = "model_data"):
        self.storage_dir = storage_dir
        os.makedirs(self.storage_dir, exist_ok=True)

    def find(self, model_id: int) -> Optional[bytes]:
        file_path = os.path.join(self.storage_dir, f"{model_id}.pth")
        if os.path.exists(file_path):
            with open(file_path, "rb") as f:
                return f.read()
        return None
    
    def save(self, model_id: int, model_data: bytes) -> Optional[bytes]:
        file_path = os.path.join(self.storage_dir, f"{model_id}.pth")
        with open(file_path, "wb") as f:
            f.write(model_data)
        return model_data
    
    def update(self, model_id: int, model_data: bytes) -> Optional[bytes]:
        return self.save(model_id, model_data)
    
    def delete(self, model_id: int) -> bool:
        file_path = os.path.join(self.storage_dir, f"{model_id}.pth")
        if os.path.exists(file_path):
            os.remove(file_path)
            return True
        return False