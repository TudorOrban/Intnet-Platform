
import os
from dotenv import load_dotenv
import mlflow

from config.logger_config import configure_logging
from core.synthetic_data_generation.base.data_generators.sample_manager.flat_sample_generator import generate_flat_samples
from core.synthetic_data_generation.base.data_generators.sample_manager.sample_generator import generate_samples
from core.synthetic_data_generation.base.data_pipeline.gnn_data_pipeline import map_flat_samples_to_pytorch_data, map_samples_to_pytorch_data
from core.synthetic_data_generation.base.data_repository.flat_training_sample_repository import FlatTrainingSampleRepository
from core.synthetic_data_generation.base.data_repository.training_sample_repository import TrainingSampleRepository
from core.synthetic_data_generation.base.model.base_gnn import train_gnn

load_dotenv()

def main():
    mlflow.set_tracking_uri(os.getenv("MLFLOW_TRACKING_URI"))
    configure_logging()

    # Flat samples
    flatSampleRepository = FlatTrainingSampleRepository()

    # samples = generate_flat_samples(topologies=2, specifications=2, records=3)

    # flatSampleRepository.add_samples(samples)

    samples = flatSampleRepository.read_samples()

    input_data = map_flat_samples_to_pytorch_data(samples)

    epochs = 100
    hidden_channels = 64
    lr = 0.01

    train_gnn(input_data=input_data, epochs=epochs, hidden_channels=hidden_channels, lr=lr)


    # Nested samples

    # samples = generate_samples(topologies=2, specifications=2, records=2)

    # sampleRepository = TrainingSampleRepository()

    # sampleRepository.add_samples(samples)
    # samples = sampleRepository.read_samples()

    # input_data = map_samples_to_pytorch_data(samples)

    # epochs = 100
    # hidden_channels = 64
    # lr = 0.01

    # train_gnn(input_data=input_data, epochs=epochs, hidden_channels=hidden_channels, lr=lr)

if __name__ == "__main__":
    main()