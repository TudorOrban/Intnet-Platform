from typing import List
from fastapi import APIRouter

from core.config.service_manager import get_service
from features.dynamic_data_record.models.record_types import DynamicDataRecord
from features.grid_graph.models.grid_graph_types import GridGraph

router = APIRouter()

record_base_route = "/api/v1/dynamic-data-records"


@router.get(record_base_route + "/{record_id}", response_model=DynamicDataRecord)
async def get_record(record_id: int):
    repo = get_service("dynamic_data_record_repository")

    record = repo.find_by_id(record_id)
    return record

@router.get(record_base_route, response_model=List[DynamicDataRecord])
async def get_all_records(limit: int = 1000):
    repo = get_service("dynamic_data_record_repository")
    return repo.find_all(limit)

@router.post(record_base_route, response_model=DynamicDataRecord)
async def create_record(record: DynamicDataRecord):
    repo = get_service("dynamic_data_record_repository")
    
    repo.save(record)
    return record

@router.post(record_base_route + "/batch", response_model=List[DynamicDataRecord])
async def create_records(records: List[DynamicDataRecord]):
    repo = get_service("dynamic_data_record_repository")
    
    repo.save_all(records)
    return records

@router.delete(record_base_route + "/{record_id}")
async def delete_record(record_id: int):
    repo = get_service("dynamic_data_record_repository")
    
    repo.delete_by_id(record_id)