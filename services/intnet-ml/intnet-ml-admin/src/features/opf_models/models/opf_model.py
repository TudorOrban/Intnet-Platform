import uuid

from sqlalchemy import UUID, Column, DateTime, String, func

from core.config.db.database_connection import Base


class OPFModel(Base):
    __tablename__ = "opf_models"
    id = Column(UUID(as_uuid=True), primary_key=True, default=uuid.uuid4)
    name = Column(String, nullable=False)
    created_at = Column(DateTime(timezone=True), server_default=func.now())
    storage_path = Column(String)
    description = Column(String)
    version = Column(String)

OPFModel.__table__