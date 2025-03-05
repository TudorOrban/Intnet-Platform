import mlflow

from core.synthetic_data_generation.base.data_pipeline.gnn_data_pipeline import map_flat_samples_to_pytorch_data
from core.synthetic_data_generation.base.data_repository.grid_graph_repository_creator import create_grid_graph_repository
from core.synthetic_data_generation.base.data_repository.json.grid_graph_json_repository import GridGraphJsonRepository
from core.synthetic_data_generation.base.model.base_gnn import train_gnn

def run_mlflow_train_gnn():
    with mlflow.start_run():
        samples_count = 72
        epochs = 100
        hidden_channels = 256
        lr = 0.01
        weight_decay=1e-5
        dropout_rate=0
        patience = 10

        mlflow.log_param("samples_count", samples_count)
        mlflow.log_param("epochs", epochs)
        mlflow.log_param("hidden_channels", hidden_channels)
        mlflow.log_param("lr", lr)
        mlflow.log_param("weight_decay", weight_decay)
        mlflow.log_param("dropout_rate", dropout_rate)
        mlflow.log_param("patience", patience)

        grid_graph_repository = create_grid_graph_repository()
        grid_graphs = grid_graph_repository.find_all(limit=samples_count)
        training_samples = [grid_graph.graph_data for grid_graph in grid_graphs]

        input_data = map_flat_samples_to_pytorch_data(training_samples)
        
        trained_model = train_gnn(input_data=input_data, epochs=epochs, hidden_channels=hidden_channels, lr=lr, weight_decay=weight_decay, dropout_rate=dropout_rate, patience=patience)
        
        mlflow.pytorch.log_model(trained_model, "model")
        mlflow.set_tags({"model_type": "GCN", "dataset": "synthetic"})