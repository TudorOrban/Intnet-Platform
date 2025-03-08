from typing import List
from fastapi import APIRouter

from features.dynamic_data_record.models.record_types import DynamicDataRecord
from features.dynamic_data_record.repositories.dynamic_data_record_repository import DynamicDataRecordRepository
from features.grid_graph.models.grid_graph_types import GridGraph

# router = APIRouter()

record_base_route = "/api/v1/dynamic-data-records"

def create_record_router(record_repository: DynamicDataRecordRepository):
    router = APIRouter()

    @router.get(record_base_route + "/{record_id}", response_model=DynamicDataRecord)
    async def get_record(record_id: int):
        record = record_repository.find_by_id(record_id)
        return record

    @router.get(record_base_route, response_model=List[DynamicDataRecord])
    async def get_all_records(limit: int = 1000):
        return record_repository.find_all(limit)

    @router.post(record_base_route, response_model=DynamicDataRecord)
    async def create_record(record: DynamicDataRecord):
        record_repository.save(record)
        return record

    @router.post(record_base_route + "/batch", response_model=List[DynamicDataRecord])
    async def create_records(records: List[DynamicDataRecord]):
        record_repository.save_all(records)
        return records

    @router.delete(record_base_route + "/{record_id}")
    async def delete_record(record_id: int):
        record_repository.delete_by_id(record_id)

    return router