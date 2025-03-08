
import structlog


logger = structlog.get_logger(__name__)

def mongo_connection_check(func):
    def wrapper(self, *args, **kwargs):
        if self.collection is None:
            logger.error("MongoDB connection is not established.")
            return None
        return func(self, *args, **kwargs)
    return wrapper
