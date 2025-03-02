from typing import Any, Dict, List, Tuple
import pandapower as pp
import numpy as np

def generate_synthetic_opf_data(num_samples: int = 100) -> Tuple[List[Dict[str, Any]], List[Dict[str, Any]]]:
    """Generate synthetic OPF data using pandapower."""
    data = []

    for _ in range(num_samples):
        net = pp.create_empty_network()

        bus1 = pp.create_bus(net, vn_kv=20.)
        bus2 = pp.create_bus(net, vn_kv=20.)
        bus3 = pp.create_bus(net, vn_kv=20.)

        pp.create_line(net, from_bus=bus1, to_bus=bus2, length_km=10., std_type="NAYY 4x50 SE")
        pp.create_line(net, from_bus=bus2, to_bus=bus3, length_km=10., std_type="NAYY 4x50 SE")

        gen_p_mw = np.random.uniform(20, 50)
        gen_min_p_mw = 0.0
        gen_max_p_mw = gen_p_mw * 1.5

        gen1 = pp.create_gen(net, bus=bus1, p_mw=gen_p_mw, min_p_mw=gen_min_p_mw, max_p_mw=gen_max_p_mw)
        gen2 = pp.create_gen(net, bus=bus2, p_mw=gen_p_mw, min_p_mw=gen_min_p_mw, max_p_mw=gen_max_p_mw)

        load_p_mw = np.random.uniform(10, 40)
        load_q_mvar = load_p_mw * 0.1 # 10% reactive power
        pp.create_load(net, bus=bus1, p_mw=load_p_mw, q_mvar=load_q_mvar)
        pp.create_load(net, bus=bus2, p_mw=load_p_mw, q_mvar=load_q_mvar)
        pp.create_load(net, bus=bus3, p_mw=load_p_mw, q_mvar=load_q_mvar)

        pp.create_ext_grid(net, bus=bus1)

        pp.create_poly_cost(net, element=gen1, et="gen", cp1_eur_per_mw=10)
        pp.create_poly_cost(net, element=gen2, et="gen", cp1_eur_per_mw=15)

        pp.runopp(net)

        data.append(net)

    return data

generated_data = generate_synthetic_opf_data(3)
print(generated_data[0].res_bus)