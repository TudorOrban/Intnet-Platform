
import os
import uuid

from sqlalchemy import UUID, Column, DateTime, String, create_engine, func
from sqlalchemy.orm import sessionmaker
from sqlalchemy.ext.declarative import declarative_base

DATABASE_URL = os.getenv("DATABASE_URL", "postgresql://user:password@localhost/ml_admin")
engine = create_engine(DATABASE_URL)
SessionLocal = sessionmaker(autoflush=False, autocommit=False, bind=engine)
Base = declarative_base()


class Model(Base):
    __tablename__ = "opf_models"
    id = Column(UUID(as_uuid=True), primary_key=True, default=uuid.uuid4)
    name = Column(String, nullable=False)
    created_at = Column(DateTime(timezone=True), server_default=func.now())
    storage_path = Column(String)
    description = Column(String)
    version = Column(String)

Base.metadata.create_all(bind=engine)
