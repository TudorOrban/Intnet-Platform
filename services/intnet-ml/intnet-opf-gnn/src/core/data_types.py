
from dataclasses import dataclass
from enum import Enum
from typing import List, Optional


# Generators
@dataclass
class GeneratorState:
    p_mw: float
    q_mvar: float
    cp1_eur_per_mw: float

class GeneratorType(Enum):
    NUCLEAR = "NUCLEAR"
    COAL = "COAL"
    GAS = "GAS"

@dataclass
class Generator:
    id: int
    bus_id: int
    min_p_mw: float
    max_p_mw: float
    min_q_mvar: float
    max_q_mvar: float
    slack: bool
    state: GeneratorState

# Loads
@dataclass
class LoadState:
    p_mw: float
    q_mvar: float

@dataclass
class Load:
    id: int
    bus_id: int
    min_p_mw: float
    max_p_mw: float
    min_q_mvar: float
    max_q_mvar: float
    state: LoadState

# Buses
@dataclass
class BusState:
    vm_pu: float
    va_deg: float
    p_inj_mw: float
    q_inj_mvar: float
    tap_pos: Optional[float]

class BusType(Enum):
    PQ = "PQ"
    PV = "PV"
    REF = "REF"

@dataclass
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

# Edges
@dataclass
class EdgeState:
    p_flow_mw: float
    q_flow_mvar: float
    i_ka: float
    in_service: bool

class EdgeType(Enum):
    TRANSMISSION_LINE = "TRANSMISSION_LINE"
    DISTRIBUTION_LINE = "DISTRIBUTION_LINE"
    TRANSFORMER = "TRANSFORMER"

@dataclass
class Edge:
    id: int
    src_bus_id: int
    dest_bus_id: int
    edge_type: EdgeType
    length_km: float
    r_ohm_per_km: float
    x_ohm_per_km: float
    state: EdgeState

@dataclass
class GridGraph:
    buses: List[Bus]
    edges: List[Edge]