from typing import List
import kfp
from kfp import dsl

@dsl.component(
    base_image="intnet-opf-gnn:latest"
)
def train_opf_gnn(
    base_graph: dict,
    records: List[dict],
    learning_rate: float
):
    pass # Docker image's entrypoint runs the training