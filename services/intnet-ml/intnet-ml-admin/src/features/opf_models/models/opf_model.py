from sqlalchemy import Column, DateTime, Integer, String, func

from core.config.db.database_connection import Base


class OPFModel(Base):
    __tablename__ = "opf_models"
    id = Column(Integer, primary_key=True, index=True)
    name = Column(String, nullable=False)
    created_at = Column(DateTime(timezone=True), server_default=func.now())
    storage_path = Column(String)
    description = Column(String)
    version = Column(String)