
import json
from pathlib import Path
from typing import List

from core.synthetic_data_generation.base.data_generators.solution_generator.sample_types import FixedTopologySample
from core.synthetic_data_generation.common.graph_data_deserializer import GraphDataDeserializer


class TrainingSampleRepository:
    def __init__(self, file_path: str = "synthetic_data/training_samples.json"):
        self.file_path = Path(file_path)
        self.file_path.parent.mkdir(parents=True, exist_ok=True)

    def read_samples(self) -> List[FixedTopologySample]:
        if not self.file_path.exists():
            return []

        with open(self.file_path, "r") as f:
            data = json.load(f)

        samples = []
        for sample_data in data:
            samples.append(GraphDataDeserializer.deserialize_fixed_topology_sample(sample_data))

        return samples

    def add_sample(self, sample: FixedTopologySample):
        samples = self.read_samples()
        samples.append(sample)

        data = [GraphDataDeserializer.serialize_fixed_topology_sample(s) for s in samples]

        with open(self.file_path, "w") as f:
            json.dump(data, f, indent=4)

    def add_samples(self, new_samples: List[FixedTopologySample]):
        samples = self.read_samples()
        samples.extend(new_samples)

        data = [GraphDataDeserializer.serialize_fixed_topology_sample(s) for s in samples]

        with open(self.file_path, "w") as f:
            json.dump(data, f, indent=4)