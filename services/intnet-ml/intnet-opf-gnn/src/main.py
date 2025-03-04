
import os
from dotenv import load_dotenv
import mlflow

from config.logger_config import configure_logging
from core.synthetic_data_generation.base.data_generators.sample_manager.sample_generator import generate_samples
from core.synthetic_data_generation.base.data_repository.training_sample_repository import TrainingSampleRepository

load_dotenv()

def main():
    mlflow.set_tracking_uri(os.getenv("MLFLOW_TRACKING_URI"))
    configure_logging()

    samples = generate_samples(topologies=2, specifications=2, records=2)

    # sampleRepository = TrainingSampleRepository()
    # sampleRepository.add_samples(samples)

if __name__ == "__main__":
    main()