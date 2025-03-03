
import os
from dotenv import load_dotenv
import mlflow
import pandapower as pp

from core.synthetic_data_generation.base.data_generators.random_dynamic_data_generator import generate_random_dynamic_data
from core.synthetic_data_generation.base.data_generators.random_grid_topology_generator import generate_random_topology
from core.synthetic_data_generation.base.data_generators.random_static_data_generator import generate_random_static_data
from core.synthetic_data_generation.base.data_generators.solution_generator.network_builder import build_pandapower_network
from core.synthetic_data_generation.base.data_repository.synthetic_graph_repository import SyntheticGraphRepository

load_dotenv()

def main():
    mlflow.set_tracking_uri(os.getenv("MLFLOW_TRACKING_URI"))

    # experiment_mlflow_run()

    graph_data = generate_random_topology(num_buses=10, num_generators=2, num_loads=4, edge_density=0.3)

    graph_data = generate_random_static_data(graph_data)

    graph_data = generate_random_dynamic_data(graph_data)

    net = build_pandapower_network(graph_data)

    pp.runopp(net)

    print(net.res_gen)

    # graphRepository = SyntheticGraphRepository()
    # graphRepository.add_graph(graph_data)

if __name__ == "__main__":
    main()