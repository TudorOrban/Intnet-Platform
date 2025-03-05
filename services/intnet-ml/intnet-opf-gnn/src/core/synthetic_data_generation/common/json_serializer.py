
from datetime import datetime
from dataclasses import asdict
import dataclasses
from datetime import datetime
from enum import Enum
from typing import Any, Dict


class JsonSerializer:

    @staticmethod
    def serialize_dataclass(obj: Any) -> Any:
        if isinstance(obj, Enum):
            return obj.value
        elif dataclasses.is_dataclass(obj):
            return {k: JsonSerializer.serialize_dataclass(v) for k, v in asdict(obj).items()}
        elif isinstance(obj, list):
            return [JsonSerializer.serialize_dataclass(item) for item in obj]
        elif isinstance(obj, datetime):
            return obj
        elif isinstance(obj, dict):
            return {k: JsonSerializer.serialize_dataclass(v) for k, v in obj.items()}
        else:
            return obj

    @staticmethod
    def deserialize_dataclass(data: Dict[str, Any], cls: type) -> Any:
        if not dataclasses.is_dataclass(cls):
            if issubclass(cls, Enum):
                return cls(data)
            return data
        field_types = {f.name: f.type for f in dataclasses.fields(cls)}
        kwargs: Dict[str, Any] = {}
        for k, v in data.items():
            field_type = field_types.get(k)
            if dataclasses.is_dataclass(field_type) or (hasattr(field_type, '__origin__') and field_type.__origin__ is list and dataclasses.is_dataclass(field_type.__args__[0])):
                if isinstance(v, list):
                    kwargs[k] = [JsonSerializer.deserialize_dataclass(item, field_type.__args__[0]) for item in v]
                else:
                    kwargs[k] = JsonSerializer.deserialize_dataclass(v, field_type)
            elif isinstance(field_type, type) and issubclass(field_type, Enum):
                kwargs[k] = field_type(v)
            else:
                kwargs[k] = v
        return cls(**kwargs)
