from typing import Any, Dict
from dotenv import load_dotenv
import mlflow # type: ignore
import torch

from experiment.models import SimpleGCN
from torch_geometric.data import Data
from torch.nn import MSELoss
from torch.optim import Adam

def train_model(model: SimpleGCN, graph: Data, epochs: int = 100, learning_rate: float = 0.01) -> None:
    optimizer: Adam = torch.optim.Adam(model.parameters(), lr=learning_rate)
    criterion: MSELoss = torch.nn.MSELoss()

    for epoch in range(epochs):
        optimizer.zero_grad()
        out: torch.Tensor = model(graph)
        loss: torch.Tensor = criterion(out, graph.y)
        loss.backward()
        optimizer.step()

        mlflow.log_metric("loss", loss.item(), step=epoch)

        if epoch % 10 == 0:
            print(f"Epoch {epoch}, Loss: {loss.item()}")
