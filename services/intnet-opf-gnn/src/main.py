
from dotenv import load_dotenv
import torch

from core.data import generate_synthetic_graph
from core.models import SimpleGCN
from torch_geometric.data import Data
from torch.nn import MSELoss
from torch.optim import Adam

load_dotenv()

def train_model(model: SimpleGCN, graph: Data, epochs: int = 100, learning_rate: float = 0.01) -> None:
    optimizer: Adam = torch.optim.Adam(model.parameters(), lr=learning_rate)
    criterion: MSELoss = torch.nn.MSELoss()

    for epoch in range(epochs):
        optimizer.zero_grad()
        out: torch.Tensor = model(graph)
        loss: torch.Tensor = criterion(out, graph.y)
        loss.backward()
        optimizer.step()

        if epoch % 10 == 0:
            print(f"Epoch {epoch}, Loss: {loss.item()}")

def main():
    graph: Data = generate_synthetic_graph()
    model: SimpleGCN = SimpleGCN(in_channels=1, hidden_channels=16, out_channels=1)
    train_model(model, graph)

if __name__ == "__main__":
    main()