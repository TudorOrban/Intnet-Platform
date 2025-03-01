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
    from example.data import generate_synthetic_graph
    from example.models import SimpleGCN
    from torch_geometric.data import Data
    from example.training import train_model

    load_dotenv()
    mlflow.set_tracking_uri(os.getenv("MLFLOW_TRACKING_URI"))

    with mlflow.start_run():
        mlflow.log_params(hyperparameters)

        graph: Data = generate_synthetic_graph()
        model: SimpleGCN = SimpleGCN(in_channels=1, hidden_channels=hyperparameters["hidden_channels"], out_channels=1)
        train_model(model, graph, epochs=hyperparameters["epochs"], learning_rate=hyperparameters["learning_rate"])
        mlflow.pytorch.log_model(model, "model")
        mlflow.set_tags({"model_type": "GCN", "dataset": "synthetic"})


@dsl.pipeline(
    name="GNN Training Pipeline",
    description="A pipeline for training a GNN model."
)
def gnn_training_pipeline(
    learning_rate: float = 0.01,
    hidden_channels: int = 16,
    epochs: int = 100
):
    train_gnn(hyperparameters={"learning_rate": learning_rate, "hidden_channels": hidden_channels, "epochs": epochs})

if __name__ == "__main__":
    kfp.compiler.Compiler().compile(
        pipeline_func=gnn_training_pipeline,
        package_path="gnn_pipeline.yaml"
    )