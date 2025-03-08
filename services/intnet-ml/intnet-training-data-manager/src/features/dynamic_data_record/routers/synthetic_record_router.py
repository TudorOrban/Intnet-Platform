from typing import List
from fastapi import APIRouter, Query

from features.dynamic_data_record.models.record_types import DynamicDataRecord
from features.dynamic_data_record.services.record_generator_service import RecordGeneratorService


record_base_route = "/api/v1/synthetic-dynamic-data-records"

def create_synthetic_record_router(
    record_generator_service: RecordGeneratorService
):
    router = APIRouter()

    @router.post(record_base_route + "/generate", response_model=List[DynamicDataRecord])
    async def generate_records(
        count: int = Query(10, description="Number of records to generate"),
        try_limit: int = Query(100, description="Maximum attempts to generate unique records")
    ):
        return record_generator_service.generate_and_save_synthetic_records(count, try_limit)

    return router