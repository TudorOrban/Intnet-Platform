
import kfp

from features.model_training.services.kubeflow_manager_service import KubeflowManagerService


class KubeflowManagerServiceImpl(KubeflowManagerService):

    def submit_kubeflow_pipeline(self, pipeline_package_path: str, arguments: dict, experiment_name: str, run_name: str):
        """Submits a Kubeflow pipeline"""
        
        client = kfp.Client()
        experiment = client.create_experiment(experiment_name)
        run = client.run_pipeline(
            experiment_id=experiment.id,
            job_name=run_name,
            pipeline_package_path=pipeline_package_path,
            params=arguments
        )
        return run