import pandapower as pp
import numpy as np

def generate_synthetic_data(num_samples=100):
    """Generates data points for GNN training."""
    



def generate_random_network():
    """Generates a random pandapower network that converges."""
    net = pp.create_empty_network()

    # Create buses
    bus1 = pp.create_bus(net, vn_kv=20., type='b')
    bus2 = pp.create_bus(net, vn_kv=20., type='b')
    bus3 = pp.create_bus(net, vn_kv=20., type='b')
    bus4 = pp.create_bus(net, vn_kv=20., type='b')
    bus5 = pp.create_bus(net, vn_kv=20., type='b')

    # Create lines (radial topology)
    pp.create_line(net, from_bus=bus1, to_bus=bus2, length_km=2., std_type="NAYY 4x150 SE")
    pp.create_line(net, from_bus=bus2, to_bus=bus3, length_km=1., std_type="NAYY 4x150 SE")
    pp.create_line(net, from_bus=bus2, to_bus=bus4, length_km=1.5, std_type="NAYY 4x150 SE")
    pp.create_line(net, from_bus=bus5, to_bus=bus3, length_km=1., std_type="NAYY 4x150 SE")

    # Create loads
    pp.create_load(net, bus=bus2, p_mw=30., q_mvar=5.)
    pp.create_load(net, bus=bus3, p_mw=25., q_mvar=4.)
    pp.create_load(net, bus=bus4, p_mw=20., q_mvar=3.)

    # Gen 1 (Slack)
    pp.create_gen(net, bus=bus1, p_mw=40., q_mvar=10., min_p_mw=0., max_p_mw=100., slack=True, max_q_mvar=100)
    pp.create_poly_cost(net, element=0, et="gen", cp1_eur_per_mw=10.)

    # Gen 2 (PQ)
    pp.create_gen(net, bus=bus5, p_mw=30., q_mvar=7., min_p_mw=0., max_p_mw=80., max_q_mvar=100)
    pp.create_poly_cost(net, element=1, et="gen", cp1_eur_per_mw=15.)

    return net

net = generate_random_network()

# pp.runpp(net)
pp.runopp(net)

print(net.res_bus)
print(net.res_gen)
