
import os
from dotenv import load_dotenv
import mlflow

from core.synthetic_data_generation.base.data_generators.random_grid_topology_generator import generate_random_topology
from core.synthetic_data_generation.base.data_repository.synthetic_graph_repository import SyntheticGraphRepository

load_dotenv()

def main():
    mlflow.set_tracking_uri(os.getenv("MLFLOW_TRACKING_URI"))

    # experiment_mlflow_run()

    graph_data = generate_random_topology(num_buses=10, num_generators=2, num_loads=4, edge_density=0.3)
    print("Grid Graph", graph_data)

    graphRepository = SyntheticGraphRepository()
    graphRepository.add_graph(graph_data=graph_data)

if __name__ == "__main__":
    main()