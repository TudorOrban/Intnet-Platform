import kfp
from kfp import dsl
from typing import Dict, Any

@dsl.component(
    base_image="intnet-opf-gnn:latest"
)
def train_gnn(hyperparameters: Dict[str, Any]):
    import os
    from dotenv import load_dotenv
    import mlflow
    from core.data import generate_synthetic_graph
    from core.models import SimpleGCN
    from torch_geometric.data import Data
    from core.training import train_model

    load_dotenv()
    mlflow.set_tracking_uri(os.getenv("MLFLOW_TRACKING_URI"))

    with mlflow.start_run():
        mlflow.log_params(hyperparameters)

        graph: Data = generate_synthetic_graph()
        model: SimpleGCN = SimpleGCN(in_channels=1, hidden_channels=hyperparameters["hidden_channels"], out_channels=1)
        train_model(model, graph, epochs=hyperparameters["epochs"], learning_rate=hyperparameters["learning_rate"])
        mlflow.pytorch.log_model(model, "model")
        mlflow.set_tags({"model_type": "GCN", "dataset": "synthetic"})