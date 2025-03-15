import kfp.compiler as compiler

from pipelines.opf_gnn_training_pipeline import train_opf_gnn_pipeline

if __name__ == "__main__":
    compiler.Compiler().compile(train_opf_gnn_pipeline, "opf_gnn_training_pipeline.yaml")