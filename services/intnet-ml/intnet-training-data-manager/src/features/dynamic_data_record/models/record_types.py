from datetime import datetime
from typing import Dict
from pydantic import BaseModel

from features.grid_graph.models.grid_graph_types import BusState, DERState, EdgeState, GeneratorState, LoadState, StorageUnitState


class RecordData(BaseModel):
    bus_data: Dict[int, BusState]
    edge_data: Dict[int, EdgeState]
    generator_data: Dict[int, GeneratorState]
    load_data: Dict[int, LoadState]
    der_data: Dict[int, DERState]
    storage_unit_data: Dict[int, StorageUnitState]

class DynamicDataRecord(BaseModel):
    id: int
    grid_id: int
    created_at: datetime
    record_data: RecordData