
from datetime import datetime
from enum import Enum
from typing import List, Optional
from pydantic import BaseModel


# Generators
class GeneratorState(BaseModel):
    generator_id: int
    p_mw: float
    q_mvar: float
    cp1_eur_per_mw: float

class GeneratorType(str, Enum):
    HYDRO = "HYDRO"
    NUCLEAR = "NUCLEAR"
    COAL = "COAL"
    GAS = "GAS"
    OIL = "OIL"

class Generator(BaseModel):
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
class LoadState(BaseModel):
    load_id: int
    p_mw: float
    q_mvar: float

class LoadType(str, Enum):
    RESIDENTIAL = "RESIDENTIAL"
    COMMERCIAL = "COMMERCIAL"
    INDUSTRIAL = "INDUSTRIAL"   
    UNKNOWN = "UNKNOWN"

class Load(BaseModel):
    id: int
    bus_id: int
    load_type: LoadType
    min_p_mw: float
    max_p_mw: float
    min_q_mvar: float
    max_q_mvar: float
    state: LoadState

# DERs
class DERState(BaseModel):
    der_id: int
    p_mw: float
    q_mvar: float

class DERType(str, Enum):
    SOLAR = "SOLAR"
    WIND = "WIND"

class DER(BaseModel):
    id: int
    bus_id: int
    der_type: DERType
    min_p_mw: float
    max_p_mw: float
    min_q_mvar: float
    max_q_mvar: float
    state: DERState

# Storage units
class StorageUnitState(BaseModel):
    storage_unit_id: int
    p_mw: float
    q_mvar: float
    soc_percent: float

class StorageUnitType(str, Enum):
    BATTERY = "BATTERY"
    PUMPED_HYDRO = "PUMPED_HYDRO"

class StorageUnit(BaseModel):
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
class BusState(BaseModel):
    bus_id: int
    vm_pu: float
    va_deg: float
    p_inj_mw: float
    q_inj_mvar: float
    tap_pos: Optional[float]

class BusType(str, Enum):
    PQ = "PQ"
    PV = "PV"
    REF = "REF"

class Bus(BaseModel):
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
class EdgeState(BaseModel):
    edge_id: int
    p_flow_mw: float
    q_flow_mvar: float
    i_ka: float
    in_service: bool

class EdgeType(str, Enum):
    TRANSMISSION_LINE = "TRANSMISSION_LINE"
    DISTRIBUTION_LINE = "DISTRIBUTION_LINE"
    TRANSFORMER = "TRANSFORMER"

class Edge(BaseModel):
    id: int
    src_bus_id: int
    dest_bus_id: int
    edge_type: EdgeType
    length_km: float
    r_ohm_per_km: float
    x_ohm_per_km: float
    state: EdgeState

class GridGraphData(BaseModel):
    buses: List[Bus]
    edges: List[Edge]

class GridGraph(BaseModel):
    id: int
    grid_id: int
    created_at: datetime
    graph_data: GridGraphData