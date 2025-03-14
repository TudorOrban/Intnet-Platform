
from abc import ABC
from typing import List, Optional

from sqlalchemy.orm import Session

from features.opf_models.models.opf_model import OPFModel
from features.opf_models.repositories.opf_model_repository import OPFModelRepository


class OPFModelRepositoryImpl(OPFModelRepository):
    def __init__(self, db: Session):
        self.db = db

    def find_by_id(self, id: int) -> Optional[OPFModel]:
        return self.db.query(OPFModel).filter(OPFModel.id == id).first()

    def find_all(self) -> List[OPFModel]:
        return self.db.query(OPFModel).all()

    def create(self, opf_model: OPFModel) -> OPFModel:
        self.db.add(opf_model)
        self.db.commit()
        self.db.refresh(opf_model)
        return opf_model
    
    def update(self, opf_model: OPFModel) -> Optional[OPFModel]:
        existing_model = self.find_by_id(opf_model.id)
        if not existing_model:
            return None
        
        existing_model.name = opf_model.name
        existing_model.storage_path = opf_model.storage_path
        existing_model.description = opf_model.description
        existing_model.version = opf_model.version
        
        self.db.commit()
        self.db.refresh(existing_model)

        return existing_model
    
    def delete_by_id(self, id: int) -> bool:
        existing_model = self.find_by_id(id)
        if not existing_model:
            return False
        
        self.db.delete(existing_model)
        self.db.commit()
        return True