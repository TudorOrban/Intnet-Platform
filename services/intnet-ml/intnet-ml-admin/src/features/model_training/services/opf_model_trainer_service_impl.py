
import os
from typing import Optional
from features.model_training.internal.to.training_data_manager.training_data_manager_client import TrainingDataManagerClient
from features.model_training.models.train_model_request import TrainOPFModelRequest
from features.model_training.services.kubeflow_manager_service import KubeflowManagerService
from features.model_training.services.opf_model_trainer_service import OPFModelTrainerService


class OPFModelTrainerServiceImpl(OPFModelTrainerService):
    def __init__(
        self,
        training_data_manager_client: TrainingDataManagerClient,
        kubeflow_manager_service: KubeflowManagerService
    ):
        self.training_data_manager_client = training_data_manager_client
        self.kubeflow_manager_service = kubeflow_manager_service

    async def train_opf_model(self, request: TrainOPFModelRequest) -> Optional[bytes]:
        """Trains a GNN model using given hyperparameters and data from Training Data Manager"""
        grid_graph = await self.training_data_manager_client.get_graph_by_grid_id(request.grid_id)
        grid_records = await self.training_data_manager_client.get_records_by_grid_id(request.grid_id)


        pipeline_package_path = os.path.join(os.path.dirname(__file__), "../../../training_pipelines/opf/opf_gnn_training_pipeline.yaml")
        arguments = {
            "base_graph": grid_graph,
            "records": grid_records,
            "learning_rate": request.hyperparameters.learning_rate
        }

        run = self.kubeflow_manager_service.submit_kubeflow_pipeline(
            pipeline_package_path=pipeline_package_path,
            arguments=arguments,
            experiment_name=request.experiment_name or "OPF Model Training",
            run_name=f"Training Run {request.grid_id}"
        )