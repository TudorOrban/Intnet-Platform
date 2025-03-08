
import json
from pathlib import Path
import random
from typing import List, Optional

from features.dynamic_data_record.models.record_types import DynamicDataRecord
from features.dynamic_data_record.utils.dynamic_data_record_json_mapper import DynamicDataRecordJsonMapper


class DynamicDataRecordJsonRepository:
    def __init__(self, file_path: str = "synthetic_data/grid_records.json"):
        self.file_path = Path(file_path)
        self.file_path.parent.mkdir(parents=True, exist_ok=True)


    def find_by_id(self, id: int) -> Optional[DynamicDataRecord]:
        records = self.find_all()

        for record in records:
            if record.id == id:
                return record
            
        return None

    def find_all(self, limit=1000) -> List[DynamicDataRecord]:
        if not self.file_path.exists():
            return []

        with open(self.file_path, "r") as f:
            data = json.load(f)

        records = []
        for i, record in enumerate(data):
            if limit != -1 and i > limit:
                break
            
            records.append(DynamicDataRecordJsonMapper.deserialize_dynamic_data_record(record))

        return records

    def save(self, record: DynamicDataRecord):
        records = self.find_all()
        existing_ids = {g.id for g in records}
        record.id = self._generate_unique_integer_id(existing_ids)

        records.append(record)

        data = [DynamicDataRecordJsonMapper.serialize_dynamic_data_record(s) for s in records]

        with open(self.file_path, "w") as f:
            json.dump(data, f, indent=4)

    def save_all(self, new_records: List[DynamicDataRecord]):
        records = self.find_all()
        existing_ids = {g.id for g in records}
        for record in new_records:
            record.id = self._generate_unique_integer_id(existing_ids)
            existing_ids.add(record.id) 

        records.extend(new_records)

        data = [DynamicDataRecordJsonMapper.serialize_dynamic_data_record(s) for s in records]

        with open(self.file_path, "w") as f:
            json.dump(data, f, indent=4)

    def delete_by_id(self, id: int):
        data = self.find_all()
        updated_data = [item for item in data if item.get("id") != id]
        
        data = [DynamicDataRecordJsonMapper.serialize_dynamic_data_record(s) for s in updated_data]

        with open(self.file_path, "w") as f:
            json.dump(data, f, indent=4)

    def delete_all(self):
        with open(self.file_path, "w") as f:
            json.dump([], f, indent=4)


    def _generate_unique_integer_id(self, existing_ids: set) -> int:
        while True:
            new_id = random.randint(1, 2147483647)
            if new_id not in existing_ids:
                return new_id
