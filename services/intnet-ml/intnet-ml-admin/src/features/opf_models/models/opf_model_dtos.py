
from datetime import datetime
from pydantic import BaseModel


class OPFModelSearchDto(BaseModel):
    id: int
    name: str
    created_at: datetime
    storage_path: str | None = None
    description: str | None = None
    version: str | None = None

class CreateOPFModelDto(BaseModel):
    name: str
    storage_path: str | None = None
    description: str | None = None
    version: str | None = None

class UpdateOPFModelDto(BaseModel):
    id: int
    name: str | None = None
    storage_path: str | None = None
    description: str | None = None
    version: str | None = None