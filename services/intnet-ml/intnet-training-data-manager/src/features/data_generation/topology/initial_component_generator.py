from features.grid_graph.models.grid_graph_types import DER, Bus, BusState, BusType, DERState, DERType, Edge, EdgeState, EdgeType, Generator, GeneratorState, GeneratorType, Load, LoadState, LoadType, StorageUnit, StorageUnitState, StorageUnitType


def generate_bus(id: int) -> Bus:
    return Bus(
        id=id, bus_type=BusType.PQ, latitude=0, longitude=0, 
        min_vm_pu=0, max_vm_pu=0, vn_kv=0, 
        generators=[], loads=[], ders=[], storage_units=[], state=BusState(bus_id=id, vm_pu=0, va_deg=0, p_inj_mw=0, q_inj_mvar=0, tap_pos=None)
    )

def generate_edge(id: int, u: int, v: int) -> Edge:
    return Edge(
        id=id, src_bus_id=u, dest_bus_id=v, edge_type=EdgeType.TRANSMISSION_LINE,
        length_km=0, r_ohm_per_km=0, x_ohm_per_km=0,
        state=EdgeState(edge_id=id, p_flow_mw=0, q_flow_mvar=0, i_ka=0, in_service=False)
    )

def generate_generator(id: int, bus_id: int, slack: bool) -> Generator:
    return Generator(
        id=id, bus_id=bus_id, generator_type=GeneratorType.COAL,
        min_p_mw=0, max_p_mw=0, min_q_mvar=0, max_q_mvar=0, slack=slack,
        state=GeneratorState(generator_id=id, p_mw=0, q_mvar=0, cp1_eur_per_mw=0)
    )

def generate_load(id: int, bus_id: int) -> Load:
    return Load(
        id=id, bus_id=bus_id, load_type=LoadType.RESIDENTIAL,
        min_p_mw=0, max_p_mw=0, min_q_mvar=0, max_q_mvar=0,
        state=LoadState(load_id=id, p_mw=0, q_mvar=0)
    )

def generate_der(id: int, bus_id: int) -> DER:
    return DER(
        id=id, bus_id=bus_id, der_type=DERType.SOLAR,
        min_p_mw=0, max_p_mw=0, min_q_mvar=0, max_q_mvar=0,
        state=DERState(der_id=id, p_mw=0, q_mvar=0)
    )

def generate_storage_unit(id: int, bus_id: int) -> StorageUnit:
    return StorageUnit(
        id=id, bus_id=bus_id, storage_type=StorageUnitType.BATTERY,
        min_p_mw=0, max_p_mw=0, min_q_mvar=0, max_q_mvar=0, min_e_mwh=0, max_e_mwh=0,
        state=StorageUnitState(storage_unit_id=id, p_mw=0, q_mvar=0, soc_percent=0)
    )