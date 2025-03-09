import os
from dotenv import load_dotenv
import mlflow

from config.logger_config import configure_logging


def initialize():
    load_dotenv()

    mlflow.set_tracking_uri(os.getenv("MLFLOW_TRACKING_URI"))
    
    configure_logging()

    