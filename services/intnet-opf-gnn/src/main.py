
import os
from typing import Any, Dict
from dotenv import load_dotenv
import mlflow # type: ignore

from core.data import generate_synthetic_graph
from core.models import SimpleGCN
from torch_geometric.data import Data

from core.training import train_model

load_dotenv()

def main():
    mlflow.set_tracking_uri(os.getenv("MLFLOW_TRACKING_URI"))
    
    hyperparameters: Dict[str, Any] = {
        "learning_rate": [0.01, 0.005, 0.001],
        "hidden_channels": [16, 32, 64],
        "epochs": [100, 200]
    }

    for learning_rate in hyperparameters["learning_rate"]:
        for hidden_channels in hyperparameters["hidden_channels"]:
            for epochs in hyperparameters["epochs"]:
                with mlflow.start_run():
                    mlflow.log_param("learning_rate", learning_rate)
                    mlflow.log_param("hidden_channels", hidden_channels)
                    mlflow.log_param("epochs", epochs)

                    graph: Data = generate_synthetic_graph()
                    model: SimpleGCN = SimpleGCN(in_channels=1, hidden_channels=hidden_channels, out_channels=1)
                    train_model(model, graph, epochs=epochs, learning_rate=learning_rate)
                    mlflow.pytorch.log_model(model, "model")
                    mlflow.set_tags({"model_type": "GCN", "dataset": "synthetic"})

if __name__ == "__main__":
    main()