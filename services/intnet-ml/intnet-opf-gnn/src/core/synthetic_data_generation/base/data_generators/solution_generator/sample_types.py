
from dataclasses import dataclass
from typing import Dict, List

from core.data_types import Bus, BusState, Edge, EdgeState, Generator, GeneratorState, GridGraphData, Load, LoadState

@dataclass
class BusFixedSpecificationSamples:
    bus: Bus
    bus_states: List[BusState] 

@dataclass
class EdgeFixedSpecificationSamples:
    edge: Edge
    edge_states: List[EdgeState]
    
@dataclass
class GeneratorFixedSpecificationSamples:
    generator: Generator
    generator_states: List[GeneratorState]

@dataclass
class LoadFixedSpecificationSamples:
    load: Load
    load_states: List[LoadState]

@dataclass
class FixedSpecificationSample:
    bus_samples: Dict[int, BusFixedSpecificationSamples] # key: bus_id
    edge_samples: Dict[int, EdgeFixedSpecificationSamples] # key: edge_id
    generator_samples: Dict[int, GeneratorFixedSpecificationSamples] # key: generator_id
    load_samples: Dict[int, LoadFixedSpecificationSamples] # key: load_id

@dataclass
class FixedTopologySample:
    graph_topology: GridGraphData
    fixed_specification_samples: List[FixedSpecificationSample]
