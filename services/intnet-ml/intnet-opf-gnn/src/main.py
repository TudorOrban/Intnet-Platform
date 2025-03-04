
import os
from dotenv import load_dotenv
import mlflow

from config.logger_config import configure_logging
from core.synthetic_data_generation.base.data_generators.sample_manager.sample_generator import generate_samples
from core.synthetic_data_generation.base.data_pipeline.gnn_data_pipeline import prepare_data_for_gnn
from core.synthetic_data_generation.base.data_repository.training_sample_repository import TrainingSampleRepository
from core.synthetic_data_generation.base.model.base_gnn import train_gnn

load_dotenv()

def main():
    mlflow.set_tracking_uri(os.getenv("MLFLOW_TRACKING_URI"))
    configure_logging()

    # samples = generate_samples(topologies=2, specifications=2, records=2)

    sampleRepository = TrainingSampleRepository()
    samples = sampleRepository.read_samples()

    input_data = prepare_data_for_gnn(samples)

    epochs = 100
    hidden_channels = 64
    lr = 0.01

    train_gnn(input_data=input_data, epochs=epochs, hidden_channels=hidden_channels, lr=lr)

if __name__ == "__main__":
    main()