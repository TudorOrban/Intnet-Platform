from fastapi import FastAPI

from core.config.db.database_connection import initialize_database
from core.config.initialization.app_initializer import initialize_app
from core.config.initialization.dependency_container import Container
from features.model_training.routers.opf_model_trainer_router import create_opf_model_training_router
from features.opf_models.routers.opf_model_router import create_opf_model_router

initialize_app()
initialize_database()

app = FastAPI()
container = Container()
app.container = container

app.include_router(create_opf_model_router(container))
app.include_router(create_opf_model_training_router(container))