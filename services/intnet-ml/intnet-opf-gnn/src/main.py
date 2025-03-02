
import os
from typing import Any, Dict
from dotenv import load_dotenv
from core.data_pipeline import prepare_data_for_gnn
from core.model import train_gnn
from core.synthetic_data import generate_synthetic_data
import mlflow

# from example.data import generate_synthetic_graph
# from example.models import SimpleGCN
from torch_geometric.data import Data

# from example.training import train_model

load_dotenv()

def main():
    mlflow.set_tracking_uri(os.getenv("MLFLOW_TRACKING_URI"))

    with mlflow.start_run():
        samples_count = 30
        epochs = 100
        hidden_channels = 64
        lr = 0.01
        patience = 10
        weight_decay = 1e-5

        mlflow.log_param("samples_count", samples_count)
        mlflow.log_param("epochs", epochs)
        mlflow.log_param("lr", lr)
        mlflow.log_param("hidden_channels", hidden_channels)
        mlflow.log_param("patience", patience)
        mlflow.log_param("weight_decay", weight_decay)

        samples = generate_synthetic_data(samples_count)
        graph_data = prepare_data_for_gnn(samples)
        trained_model = train_gnn(graph_data, epochs=epochs, hidden_channels=hidden_channels, lr=lr, patience=patience, weight_decay=weight_decay)
        mlflow.pytorch.log_model(trained_model, "model")
        mlflow.set_tags({"model_type": "GCN", "dataset": "synthetic"})
    
    # hyperparameters: Dict[str, Any] = {
    #     "learning_rate": [0.01, 0.005, 0.001],
    #     "hidden_channels": [16, 32, 64],
    #     "epochs": [100, 200]
    # }

    # for learning_rate in hyperparameters["learning_rate"]:
    #     for hidden_channels in hyperparameters["hidden_channels"]:
    #         for epochs in hyperparameters["epochs"]:
    #             with mlflow.start_run():
    #                 mlflow.log_param("learning_rate", learning_rate)
    #                 mlflow.log_param("hidden_channels", hidden_channels)
    #                 mlflow.log_param("epochs", epochs)

    #                 graph: Data = generate_synthetic_graph()
    #                 model: SimpleGCN = SimpleGCN(in_channels=1, hidden_channels=hidden_channels, out_channels=1)
    #                 train_model(model, graph, epochs=epochs, learning_rate=learning_rate)
    #                 mlflow.pytorch.log_model(model, "model")
    #                 mlflow.set_tags({"model_type": "GCN", "dataset": "synthetic"})

if __name__ == "__main__":
    main()