
from dataclasses import dataclass
from typing import Dict

from core.common.data_types import BusState, DERState, EdgeState, GeneratorState, LoadState, StorageUnitState


@dataclass
class DynamicDataRecord:
    bus_data: Dict[int, BusState]
    edge_data: Dict[int, EdgeState]
    generator_data: Dict[int, GeneratorState]
    load_data: Dict[int, LoadState]
    der_data: Dict[int, DERState]
    storage_unit_data: Dict[int, StorageUnitState]