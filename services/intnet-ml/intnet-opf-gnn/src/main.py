
import os
from dotenv import load_dotenv
import mlflow
import pandapower as pp

from core.synthetic_data_generation.base.data_generators.random_dynamic_data_generator import generate_random_dynamic_data
from core.synthetic_data_generation.base.data_generators.random_grid_topology_generator import generate_random_topology
from core.synthetic_data_generation.base.data_generators.random_static_data_generator import generate_random_static_data
from core.synthetic_data_generation.base.data_generators.solution_generator.network_builder import build_pandapower_network
from core.synthetic_data_generation.base.data_generators.solution_generator.sample_generator import generate_samples
from core.synthetic_data_generation.base.data_repository.synthetic_graph_repository import SyntheticGraphRepository
from core.synthetic_data_generation.base.data_repository.training_sample_repository import TrainingSampleRepository

load_dotenv()

def main():
    mlflow.set_tracking_uri(os.getenv("MLFLOW_TRACKING_URI"))

    # experiment_mlflow_run()

    samples = generate_samples(topologies=2, specifications=2, records=2)

    sampleRepository = TrainingSampleRepository()
    sampleRepository.add_samples(samples)

    # graph_data = generate_random_topology(num_buses=20, num_generators=2, num_loads=9, edge_density=0.4)

    # samples = 15
    # convergent = 0

    # for i in range(samples):
    #     graph_data = generate_random_static_data(graph_data)

    #     graph_data = generate_random_dynamic_data(graph_data)

    #     net = build_pandapower_network(graph_data)

    #     try:
    #         pp.runopp(net)
    #         convergent = convergent + 1
    #     except Exception as e:
    #         print(f"OPP didnt converge: {e}")

    # print(f"Convergence: {convergent / samples}")

    # print(net.res_gen)
    
    # graphRepository = SyntheticGraphRepository()
    # graphRepository.add_graph(graph_data)

if __name__ == "__main__":
    main()