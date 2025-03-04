import mlflow

from core.synthetic_data_generation.base.data_pipeline.gnn_data_pipeline import map_flat_samples_to_pytorch_data
from core.synthetic_data_generation.base.data_repository.flat_training_sample_repository import FlatTrainingSampleRepository
from core.synthetic_data_generation.base.model.base_gnn import train_gnn

def run_mlflow_train_gnn():
    with mlflow.start_run():
        samples_count = 72
        epochs = 100
        hidden_channels = 256
        lr = 0.01

        mlflow.log_param("samples_count", samples_count)
        mlflow.log_param("epochs", epochs)
        mlflow.log_param("hidden_channels", hidden_channels)
        mlflow.log_param("lr", lr)

        flatSampleRepository = FlatTrainingSampleRepository()
        samples = flatSampleRepository.read_samples(limit=samples_count)
        
        input_data = map_flat_samples_to_pytorch_data(samples)
        
        trained_model = train_gnn(input_data=input_data, epochs=epochs, hidden_channels=hidden_channels, lr=lr)
        
        mlflow.pytorch.log_model(trained_model, "model")
        mlflow.set_tags({"model_type": "GCN", "dataset": "synthetic"})