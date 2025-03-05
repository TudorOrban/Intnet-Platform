
from datetime import datetime
import os
from dotenv import load_dotenv
import mlflow

from config.logger_config import configure_logging
from core.synthetic_data_generation.base.data_generators.sample_manager.flat_sample_generator import generate_flat_samples
from core.synthetic_data_generation.base.data_generators.sample_manager.sample_generator import generate_samples
from core.synthetic_data_generation.base.data_pipeline.gnn_data_pipeline import map_flat_samples_to_pytorch_data, map_samples_to_pytorch_data
from core.synthetic_data_generation.base.data_repository.grid_graph_repository_creator import create_grid_graph_repository
from core.synthetic_data_generation.base.data_repository.json.grid_graph_json_repository import GridGraphJsonRepository
from core.synthetic_data_generation.base.data_repository.json.training_sample_repository import TrainingSampleRepository
from core.synthetic_data_generation.base.data_repository.mongo.grid_graph_mongo_repository import GridGraphMongoRepository
from core.synthetic_data_generation.base.model.base_gnn import train_gnn
from core.synthetic_data_generation.base.model.mlflow_run import run_mlflow_train_gnn
from core.synthetic_data_generation.common.data_types import Bus, BusState, BusType, Edge, EdgeState, EdgeType, Generator, GeneratorState, GridGraph, GridGraphData

load_dotenv()

def main():
    mlflow.set_tracking_uri(os.getenv("MLFLOW_TRACKING_URI"))
    configure_logging()

    # Flat samples
    # flatSampleRepository = FlatTrainingSampleRepository()

    # samples = generate_flat_samples(topologies=4, specifications=3, records=6)

    # flatSampleRepository.add_samples(samples)

    # run_mlflow_train_gnn()

    repository = create_grid_graph_repository()
    graphs = repository.find_all()
    print(graphs)

if __name__ == "__main__":
    main()