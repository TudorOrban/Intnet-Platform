
from dataclasses import dataclass
from typing import Dict

from core.common.data_types import BusState, EdgeState, GeneratorState, LoadState


@dataclass
class DynamicDataRecord:
    bus_data: Dict[int, BusState]
    edge_data: Dict[int, EdgeState]
    generator_data: Dict[int, GeneratorState]
    load_data: Dict[int, LoadState]
    