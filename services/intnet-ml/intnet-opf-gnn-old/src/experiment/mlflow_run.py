import mlflow

from experiment.data_pipeline import prepare_data_for_gnn
from experiment.model import train_gnn
from experiment.synthetic_data import generate_synthetic_data

def experiment_mlflow_run():
    with mlflow.start_run():
        samples_count = 40
        epochs = 100
        hidden_channels = 128
        lr = 0.01
        patience = 10
        weight_decay = 1e-5
        dropout_rate = 0.5

        mlflow.log_param("samples_count", samples_count)
        mlflow.log_param("epochs", epochs)
        mlflow.log_param("lr", lr)
        mlflow.log_param("hidden_channels", hidden_channels)
        mlflow.log_param("patience", patience)
        mlflow.log_param("weight_decay", weight_decay)
        mlflow.log_param("dropout_rate", dropout_rate)

        samples = generate_synthetic_data(samples_count)
        graph_data = prepare_data_for_gnn(samples)
        trained_model = train_gnn(graph_data, epochs=epochs, hidden_channels=hidden_channels, lr=lr, patience=patience, weight_decay=weight_decay, dropout_rate=dropout_rate)
        mlflow.pytorch.log_model(trained_model, "model")
        mlflow.set_tags({"model_type": "GCN", "dataset": "synthetic"})
    