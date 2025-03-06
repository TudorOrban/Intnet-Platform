
import json
from pathlib import Path
from typing import List

from core.common.json_serializer import JsonSerializer
from finetuning.common.data_types import DynamicDataRecord
from finetuning.common.dynamic_data_record_deserializer import DynamicDataRecordDeserializer

"""
Repository for Dynamic Data records using JSON storage for fast iteration in model development
"""
class DynamicDataRecordJsonRepository:
    def __init__(self, file_path: str = "synthetic_data/finetuning/dynamic_data_records.json"):
        self.file_path = Path(file_path)
        self.file_path.parent.mkdir(parents=True, exist_ok=True)


    def find_all(self) -> List[DynamicDataRecord]:
        if not self.file_path.exists():
            return None
        
        with open(self.file_path, "r") as f:
            data = json.load(f)
            
        return [DynamicDataRecordDeserializer.deserialize_dynamic_data_record(record) for record in data]
    
    def save_all(self, new_records: List[DynamicDataRecord]):
        records = self.find_all()
        records.extend(new_records)

        data = JsonSerializer.serialize_dataclass(records)

        with open(self.file_path, "w") as f:
            json.dump(data, f, indent=4)