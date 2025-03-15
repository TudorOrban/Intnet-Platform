
from datetime import datetime
from enum import Enum
from typing import Dict, List, Optional


# Generators
class GeneratorState:
    generator_id: int
    p_mw: float
    q_mvar: float
    cp1_eur_per_mw: float

class GeneratorType(Enum):
    HYDRO = "HYDRO"
    NUCLEAR = "NUCLEAR"
    COAL = "COAL"
    GAS = "GAS"
    OIL = "OIL"

class Generator:
    id: int
    bus_id: int
    generator_type: GeneratorType
    min_p_mw: float
    max_p_mw: float
    min_q_mvar: float
    max_q_mvar: float
    slack: bool
    state: GeneratorState

# Loads
class LoadState:
    load_id: int
    p_mw: float
    q_mvar: float

class LoadType(Enum):
    RESIDENTIAL = "RESIDENTIAL"
    COMMERCIAL = "COMMERCIAL"
    INDUSTRIAL = "INDUSTRIAL"   
    UNKNOWN = "UNKNOWN"

class Load:
    id: int
    bus_id: int
    load_type: LoadType
    min_p_mw: float
    max_p_mw: float
    min_q_mvar: float
    max_q_mvar: float
    state: LoadState

# DERs
class DERState:
    der_id: int
    p_mw: float
    q_mvar: float

class DERType(Enum):
    SOLAR = "SOLAR"
    WIND = "WIND"

class DER:
    id: int
    bus_id: int
    der_type: DERType
    min_p_mw: float
    max_p_mw: float
    min_q_mvar: float
    max_q_mvar: float
    state: DERState

# Storage units
class StorageUnitState:
    storage_unit_id: int
    p_mw: float
    q_mvar: float
    soc_percent: float

class StorageUnitType(Enum):
    BATTERY = "BATTERY"
    PUMPED_HYDRO = "PUMPED_HYDRO"

class StorageUnit:
    id: int
    bus_id: int
    storage_type: StorageUnitType
    min_p_mw: float
    max_p_mw: float
    min_q_mvar: float
    max_q_mvar: float
    min_e_mwh: float
    max_e_mwh: float
    state: StorageUnitState

# Buses
class BusState:
    bus_id: int
    vm_pu: float
    va_deg: float
    p_inj_mw: float
    q_inj_mvar: float
    tap_pos: Optional[float]

class BusType(Enum):
    PQ = "PQ"
    PV = "PV"
    REF = "REF"

class Bus:
    id: int
    bus_type: BusType
    latitude: float
    longitude: float
    min_vm_pu: float
    max_vm_pu: float
    vn_kv: float
    state: BusState

    generators: List[Generator]
    loads: List[Load]
    ders: List[DER]
    storage_units: List[StorageUnit]

# Edges
class EdgeState:
    edge_id: int
    p_flow_mw: float
    q_flow_mvar: float
    i_ka: float
    in_service: bool

class EdgeType(Enum):
    TRANSMISSION_LINE = "TRANSMISSION_LINE"
    DISTRIBUTION_LINE = "DISTRIBUTION_LINE"
    TRANSFORMER = "TRANSFORMER"

class Edge:
    id: int
    src_bus_id: int
    dest_bus_id: int
    edge_type: EdgeType
    length_km: float
    r_ohm_per_km: float
    x_ohm_per_km: float
    state: EdgeState

class GridGraphData:
    buses: List[Bus]
    edges: List[Edge]

class GridGraph:
    id: int
    grid_id: int
    created_at: datetime
    graph_data: GridGraphData


class RecordData:
    bus_data: Dict[int, BusState]
    edge_data: Dict[int, EdgeState]
    generator_data: Dict[int, GeneratorState]
    load_data: Dict[int, LoadState]
    der_data: Dict[int, DERState]
    storage_unit_data: Dict[int, StorageUnitState]

class DynamicDataRecord:
    id: int
    grid_id: int
    created_at: datetime
    record_data: RecordData