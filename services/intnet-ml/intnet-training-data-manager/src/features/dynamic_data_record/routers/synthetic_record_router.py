from typing import List
from fastapi import APIRouter, Query

from features.dynamic_data_record.models.record_types import DynamicDataRecord
from features.dynamic_data_record.repositories.dynamic_data_record_repository import DynamicDataRecordRepository
from features.dynamic_data_record.services.record_generator_service import RecordGeneratorService


record_base_route = "/api/v1/synthetic-dynamic-data-records"

def create_synthetic_record_router(
    synthetic_record_repository: DynamicDataRecordRepository,
    record_generator_service: RecordGeneratorService
):
    router = APIRouter()

    # CRUD ops
    @router.get(record_base_route + "/{record_id}", response_model=DynamicDataRecord)
    async def get_record(record_id: int):
        return synthetic_record_repository.find_by_id(record_id)
    
    @router.get(record_base_route + "/grid/{grid_id}", response_model=List[DynamicDataRecord])
    async def get_records_by_grid_id(grid_id: int):
        return synthetic_record_repository.find_by_grid_id(grid_id)        

    @router.get(record_base_route, response_model=List[DynamicDataRecord])
    async def get_all_records(limit: int = 1000):
        return synthetic_record_repository.find_all(limit)

    @router.post(record_base_route, response_model=DynamicDataRecord)
    async def create_record(record: DynamicDataRecord):
        synthetic_record_repository.save(record)

    @router.post(record_base_route + "/batch", response_model=List[DynamicDataRecord])
    async def create_records(records: List[DynamicDataRecord]):
        synthetic_record_repository.save_all(records)

    @router.delete(record_base_route + "/{record_id}")
    async def delete_record(record_id: int):
        synthetic_record_repository.delete_by_id(record_id)

    @router.delete(record_base_route + "/grid/{grid_id}")
    async def delete_records_by_grid_id(grid_id: int):
        synthetic_record_repository.delete_by_grid_id(grid_id)

    @router.delete(record_base_route)
    async def delete_all_records():
        synthetic_record_repository.delete_all()

    # Data generation
    @router.post(record_base_route + "/generate/{grid_id}", response_model=List[DynamicDataRecord])
    async def generate_records(
        grid_id: int,
        count: int = Query(10, description="Number of records to generate"),
        try_limit: int = Query(100, description="Maximum attempts to generate unique records")
    ):
        return record_generator_service.generate_and_save_synthetic_records(grid_id, count, try_limit)

    return router