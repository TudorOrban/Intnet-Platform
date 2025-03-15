
import json
import sys

from training.opf_gnn_trainer import train_opf_gnn


if __name__ == "__main__":
    base_graph = json.loads(sys.argv[1])
    records = json.loads(sys.argv[2])
    learning_rate = float(sys.argv[3])

    train_opf_gnn(base_graph, records, learning_rate)