
from abc import ABC, abstractmethod


class KubeflowManagerService(ABC):

    @abstractmethod
    def submit_kubeflow_pipeline(pipeline_package_path: str, arguments: dict, experiment_name: str, run_name: str):
        pass