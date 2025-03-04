
from dataclasses import asdict
import dataclasses
from datetime import datetime
from enum import Enum
import os
import structlog
from typing import Any, Dict, List, Optional
from pymongo import MongoClient
from pymongo.errors import ConnectionFailure

from core.synthetic_data_generation.common.data_types import GridGraph

logger = structlog.get_logger(__name__)

class GridGraphRepository:
    def __init__(self):
        connection_string = os.getenv("MONGODB_CONNECTION_STRING")
        database_name = os.getenv("MONGODB_DATABASE_NAME")
        collection_name="grid_graphs"

        try:
            self.client = MongoClient(connection_string)
            self.db = self.client[database_name]
            self.collection = self.db[collection_name]
            self.client.admin.command("ping")
            logger.info("Successfully connected to MongoDB.")
        except ConnectionFailure as e:
            logger.error(f"Could not connect to MongoDB: {e}")
            self.client = None
            self.db = None
            self.collection = None

    
    def save(self, grid_graph: GridGraph):
        if self.collection is None:
            logger.error("MongoDB connection is not established.")
            return

        graph_dict = self._serialize_dataclass(grid_graph)
        self.collection.insert_one(graph_dict)

    def save_all(self, grid_graphs: List[GridGraph]):
        if self.collection is None:
            logger.error("MongoDB connection is not established.")
            return
        
        graphs_dict = self._serialize_dataclass(grid_graphs)
        self.collection.insert_many(graphs_dict)

    def find_by_id(self, graph_id: int) -> Optional[GridGraph]:
        if self.collection is None:
            logger.error("MongoDB connection is not established.")
            return None

        result = self.collection.find_one({"id": graph_id})
        if result:
            result.pop("_id", None) 
            return self._deserialize_dataclass(result, GridGraph)
        else:
            return None

    def find_all(self) -> List[GridGraph]:
        if self.collection is None:
            logger.error("MongoDB connection is not established.")
            return []

        results = self.collection.find()
        grid_graphs = []
        for result in results:
            result.pop("_id", None) 
            grid_graphs.append(self._deserialize_dataclass(result, GridGraph))
        return grid_graphs

    def delete_by_id(self, graph_id: int):
        if self.collection is None:
            logger.error("MongoDB connection is not established.")
            return

        self.collection.delete_one({"id": graph_id})
    
    
    def _serialize_dataclass(self, obj: Any) -> Any:
        if isinstance(obj, Enum):
            return obj.value
        elif dataclasses.is_dataclass(obj):
            return {k: self._serialize_dataclass(v) for k, v in asdict(obj).items()}
        elif isinstance(obj, list):
            return [self._serialize_dataclass(item) for item in obj]
        elif isinstance(obj, datetime):
            return obj
        elif isinstance(obj, dict):
            return {k: self._serialize_dataclass(v) for k, v in obj.items()}
        else:
            return obj

    def _deserialize_dataclass(self, data: Dict[str, Any], cls: type) -> Any:
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
                    kwargs[k] = [self._deserialize_dataclass(item, field_type.__args__[0]) for item in v]
                else:
                    kwargs[k] = self._deserialize_dataclass(v, field_type)
            elif isinstance(field_type, type) and issubclass(field_type, Enum):
                kwargs[k] = field_type(v)
            else:
                kwargs[k] = v
        return cls(**kwargs)
