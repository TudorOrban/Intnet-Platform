from typing import List
from kfp import dsl

from components.opf_gnn_training_component import train_opf_gnn


@dsl.pipeline(
    name="OPF GNN Training Pipeline",
    description="Trains a GNN using data on OPF solutions"
)
def train_opf_gnn_pipeline(
    base_graph: dict,
    records: List[dict],
    learning_rate: float = 0.01
):
    train_task = train_opf_gnn(
        base_graph=base_graph,
        records=records,
        learning_rate=learning_rate
    )