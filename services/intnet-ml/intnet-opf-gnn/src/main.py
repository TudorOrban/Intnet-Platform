
import os
from dotenv import load_dotenv
import mlflow

from core.synthetic_data_generation.base.data_generators.random_network_generator import generate_random_topology

load_dotenv()

def main():
    mlflow.set_tracking_uri(os.getenv("MLFLOW_TRACKING_URI"))

    # experiment_mlflow_run()

    grid_graph = generate_random_topology(num_buses=10, num_generators=2, num_loads=4, edge_density=0.3)
    print("Grid Graph", grid_graph)

if __name__ == "__main__":
    main()