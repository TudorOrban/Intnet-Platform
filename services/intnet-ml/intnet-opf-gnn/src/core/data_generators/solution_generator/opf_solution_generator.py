
from typing import Tuple
import structlog
import pandapower as pp

from core.data_generators.solution_generator.network_builder import build_pandapower_network
from core.common.data_types import GridGraphData

logger = structlog.get_logger(__name__)

def generate_opf_sample(graph_data: GridGraphData) -> Tuple[GridGraphData, bool]:
    """Uses pandapower's OPP to determine desired generator states"""
    net = build_pandapower_network(graph_data)

    has_converged = False

    try:
        pp.runopp(net)
        has_converged = True
        logger.info("OPF converged")
    except Exception as e:
        logger.error(f"OPF didn't converge: {e}")
    if not has_converged:
        return graph_data, False
    
    for bus in graph_data.buses:
        for generator in bus.generators:
            generator_index = net.gen.index[net.gen["name"] == str(generator.id)].tolist()[0]
            
            generator_p_mw = net.res_gen.p_mw.values[generator_index]

            generator.state.p_mw = generator_p_mw

    return graph_data, True