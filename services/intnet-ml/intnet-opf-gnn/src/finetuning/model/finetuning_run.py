import mlflow

from finetuning.data_generators.dynamic_data_record_generator import generate_dynamic_data_records
from finetuning.data_repositories.dynamic_data_record_repository_creator import create_dynamic_data_record_repository
from finetuning.data_repositories.real_grid_graph_repository_creator import create_real_grid_graph_repository
from finetuning.model.model_finetuner import finetune_model


def run_mlflow_finetune_gnn():
    with mlflow.start_run():
        samples_count = 10
        epochs = 200
        hidden_channels = 512
        lr = 0.008
        weight_decay = 1e-5
        dropout_rate = 0.01
        patience = 10

        mlflow.log_param("samples_count", samples_count)
        mlflow.log_param("epochs", epochs)
        mlflow.log_param("hidden_channels", hidden_channels)
        mlflow.log_param("lr", lr)
        mlflow.log_param("weight_decay", weight_decay)
        mlflow.log_param("dropout_rate", dropout_rate)
        mlflow.log_param("patience", patience)

        graph_repository = create_real_grid_graph_repository()
        record_repository = create_dynamic_data_record_repository()

        graph = graph_repository.find()
        # record_repository.save_all(records)
        # records = record_repository.find_all()

        records = generate_dynamic_data_records(graph_data=graph.graph_data, record_count=samples_count)
        
        finetuned_model = finetune_model(base_graph=graph.graph_data, records=records, epochs=epochs, hidden_channels=hidden_channels, lr=lr, weight_decay=weight_decay, dropout_rate=dropout_rate, patience=patience)

        mlflow.pytorch.log_model(finetuned_model, "finetuned_model")
        mlflow.set_tags({"model_type": "GCN", "dataset": "synthetic"})