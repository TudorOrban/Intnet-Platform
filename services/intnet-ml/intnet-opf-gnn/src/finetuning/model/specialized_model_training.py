

import os
from typing import List

import mlflow
import torch
from core.common.data_types import GridGraphData
from finetuning.common.data_types import DynamicDataRecord
from finetuning.data_pipeline.finetuning_data_pipeline import create_pytorch_data_from_record, preprocess_data
from finetuning.model.specialized_model import SpecializedGNN


def train_specialized_model(base_graph: GridGraphData, records: List[DynamicDataRecord], epochs=100, hidden_channels=64, lr=0.01, weight_decay=1e-5, dropout_rate=0.4, patience=10) -> SpecializedGNN:
    """Finetunes an existing model on dynamic data records"""

    train_test_ratio = 0.8
    train_size = int(train_test_ratio * len(records))
    train_records = records[:train_size]
    val_records = records[train_size:]

    edge_index, node_mean, node_std, edge_mean, edge_std, num_node_features = preprocess_data(base_graph, records)

    model = SpecializedGNN(num_node_features=num_node_features, hidden_channels=hidden_channels, dropout_rate=dropout_rate)
    optimizer = torch.optim.Adam(model.parameters(), lr, weight_decay=weight_decay)
    criterion = torch.nn.MSELoss()
    scheduler = torch.optim.lr_scheduler.ReduceLROnPlateau(optimizer, "min", patience=patience // 2, factor=0.5)

    device = os.getenv("DEVICE", "cpu")
    device = torch.device(device)
    model = model.to(device)
    edge_index = edge_index.to(device)

    best_val_loss = float("inf")
    epochs_no_improve = 0

    model.train()
    for epoch in range(epochs):
        total_train_loss = 0
        for record in train_records:
            data = create_pytorch_data_from_record(base_graph, record, edge_index, node_mean, node_std, edge_mean, edge_std)
            data = data.to(device)
            optimizer.zero_grad()
            out = model(data)
            loss = criterion(out, data.y)
            loss.backward()
            optimizer.step()
            total_train_loss += loss.item()

        model.eval()
        total_val_loss = 0
        with torch.no_grad():
            for record in val_records:
                data = create_pytorch_data_from_record(base_graph, record, edge_index, node_mean, node_std, edge_mean, edge_std)
                data = data.to(device)
                out = model(data)
                loss = criterion(out, data.y)
                total_val_loss += loss.item()
        

        avg_train_loss = total_train_loss / len(train_records)
        avg_val_loss = total_val_loss / len(val_records)
        scheduler.step(avg_val_loss)

        print(f"Epoch {epoch + 1}, Train Loss: {avg_train_loss}, Val Loss: {avg_val_loss}, LR: {optimizer.param_groups[0]['lr']}")

        mlflow.log_metric("train_loss", avg_train_loss, step=epoch)
        mlflow.log_metric("val_loss", avg_val_loss, step=epoch)
        mlflow.log_metric("learning_rate", optimizer.param_groups[0]['lr'], step=epoch)

        if avg_val_loss < best_val_loss:
            best_val_loss = avg_val_loss
            epochs_no_improve = 0
            torch.save(model.state_dict(), "best_finetuned_model.pth")
        else:
            epochs_no_improve += 1
            if epochs_no_improve == patience:
                print("Early stopping!")
                break
        model.train()

    model.load_state_dict(torch.load("best_finetuned_model.pth"))
    return model