from typing import List
from fastapi import APIRouter

from features.dynamic_data_record.models.record_types import DynamicDataRecord
from features.dynamic_data_record.repositories.dynamic_data_record_repository import DynamicDataRecordRepository
from features.dynamic_data_record.services.record_generator_service import RecordGeneratorService
from features.grid_graph.models.grid_graph_types import GridGraph

# router = APIRouter()

record_base_route = "/api/v1/dynamic-data-records"

def create_record_router(
    record_repository: DynamicDataRecordRepository,
    record_generator_service: RecordGeneratorService
):
    router = APIRouter()

    # CRUD ops
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

    # Data generation
    @router.post(record_base_route + "/generate", response_model=List[DynamicDataRecord])
    async def generate_records(count=10, limit=100):
        return record_generator_service.generate_and_save_synthetic_records(count, limit)

    return router