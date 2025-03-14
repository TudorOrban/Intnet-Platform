


from dotenv import load_dotenv
from structlog import get_logger

from core.config.initialization.logging_configurer import configure_logging


def initialize_app():
    load_dotenv()
    configure_logging()
    logger = get_logger(__name__)
    logger.info("Initializing ML Admin Service")
