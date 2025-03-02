
from dataclasses import dataclass
import datetime
from typing import List, Optional


@dataclass
class BusStateData:
    voltage_magnitude: float
    voltage_angle: float
    active_power_injection: float
    reactive_power_injection: float
    shunt_capacitor_reactor_status: bool
    phase_shifting_transformer_tap_position: Optional[float]
    updated_at: datetime

@dataclass
class BusData:
    id: int
    grid_id: int
    state: BusStateData
    bus_name: str
    created_at: datetime
    updated_at: datetime
    latitude: float
    longitude: float
    generator_ids: List[int]
    load_ids: List[int]

@dataclass
class GeneratorStateData:
    active_power_output: float
    reactive_power_output: float
    updated_at: datetime

@dataclass
class GeneratorData:
    id: int
    grid_id: int
    bus_id: int
    state: GeneratorStateData
    generator_name: str
    created_at: datetime
    updated_at: datetime
    generator_max_active_power: float
    generator_min_active_power: float
    generator_max_reactive_power: float
    generator_min_reactive_power: float

@dataclass
class LoadStateData:
    active_power_load: float
    reactive_power_load: float
    updated_at: datetime

@dataclass
class LoadData:
    id: int
    grid_id: int
    bus_id: int
    state: LoadStateData
    load_name: str
    created_at: datetime
    updated_at: datetime
    load_max_active_power: float
    load_max_reactive_power: float


@dataclass
class TransmissionLineStateData:
    active_power_flow: float
    reactive_power_flow: float
    current_magnitude: float
    line_status: bool
    updated_at: datetime

@dataclass
class TransmissionLineData:
    id: int
    grid_id: int
    from_bus_id: int
    to_bus_id: int
    state: TransmissionLineStateData
    line_name: str
    created_at: datetime
    updated_at: datetime
    resistance: float
    reactance: float
    susceptance: float
    line_length: float
    max_flow: float