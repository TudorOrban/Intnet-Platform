
import os


def load_opf_gnn_training_pipeline() -> str:
    pipeline_path = os.path.join(os.path.dirname(__file__), "../../../../training_pipelines/opf/opf_gnn_training_pipeline.yaml")
    with open(pipeline_path, "r") as f:
        pipeline_yaml = f.read()
        return pipeline_yaml
    return ""