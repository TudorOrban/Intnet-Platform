from dependency_injector import containers, providers

from core.config.db.database_connection import get_db
from features.model_training.internal.to.training_data_manager.training_data_manager_client_impl import TrainingDataManagerClientImpl
from features.model_training.services.kubeflow_manager_service_impl import KubeflowManagerServiceImpl
from features.model_training.services.opf_model_trainer_service_impl import OPFModelTrainerServiceImpl
from features.opf_models.repositories.opf_model_data_repository_creator import create_opf_model_data_repository
from features.opf_models.repositories.opf_model_repository_impl import OPFModelRepositoryImpl
from features.opf_models.services.opf_model_service_impl import OPFModelServiceImpl


class Container(containers.DeclarativeContainer):
    wiring_config = containers.WiringConfiguration(modules=["__main__"])

    db = providers.Resource(get_db)

    # OPF model
    opf_model_repository = providers.Singleton(
        OPFModelRepositoryImpl,
        db=db
    )

    opf_model_data_repository = providers.Factory(
        create_opf_model_data_repository
    )

    opf_model_service = providers.Factory(
        OPFModelServiceImpl,
        opf_model_repository=opf_model_repository,
        opf_model_data_repository=opf_model_data_repository
    )

    # OPF model training
    training_data_manager_client = providers.Factory(
        TrainingDataManagerClientImpl
    )

    kubeflow_manager_service = providers.Factory(
        KubeflowManagerServiceImpl
    )

    opf_model_trainer_service = providers.Factory(
        OPFModelTrainerServiceImpl,
        training_data_manager_client=training_data_manager_client,
        kubeflow_manager_service=kubeflow_manager_service
    )
