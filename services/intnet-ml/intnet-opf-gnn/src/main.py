
import json
from typing import List
import sys


def train_gnn(base_graph: dict, records: List[dict], learning_rate: float):
    """Entry point fro the GNN training workflow"""
    print(f"Training GNN with learning rate: {learning_rate}, and base graph: {base_graph}")


if __name__ == "__main__":
    base_graph = json.loads(sys.argv[1])
    records = json.loads(sys.argv[2])
    learning_rate = float(sys.argv[3])

    train_gnn(base_graph, records, learning_rate)