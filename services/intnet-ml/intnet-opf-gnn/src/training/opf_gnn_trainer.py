


from typing import List

from types.grid_types_json_mapper import GridTypeJsonMapper


def train_opf_gnn(base_graph: dict, records: List[dict], learning_rate: float):
    """Entry point fro the GNN training workflow"""
    print(f"Training GNN with learning rate: {learning_rate}, and base graph: {base_graph}")

    grid_graph = GridTypeJsonMapper.deserialize_grid_graph(base_graph)
    grid_records = GridTypeJsonMapper.deserialize_dynamic_data_record(records)

    samples_count = 50
    epochs = 200
    hidden_channels = 1024
    lr = 0.008
    weight_decay = 1e-5
    dropout_rate = 0.01
    patience = 10