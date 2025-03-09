from typing import Dict
import torch
from torch_geometric.data import Data

from experiment.feature_extractor import extract_bus_features, extract_line_features
from experiment.data_types import BusData, GeneratorData, TransmissionLineData


def construct_graph_data(
    buses: Dict[int, BusData],
    generators: Dict[int, GeneratorData],
    lines: Dict[int, TransmissionLineData]
) -> Data:
    node_features = torch.stack([extract_bus_features(bus) for bus in buses.values()])

    edge_list = []
    edge_features = []

    bus_index_map = {bus_id: index for index, bus_id in enumerate(buses.keys())}

    for line in lines.values():
        from_index = bus_index_map[line.from_bus_id]
        to_index = bus_index_map[line.to_bus_id]
        edge_list.append([from_index, to_index])
        edge_features.append(extract_line_features(line))

    edge_index = torch.tensor(edge_list, dtype=torch.long).t().contiguous()
    edge_attr = torch.stack(edge_features)

    y = torch.zeros(len(buses), 1, dtype=torch.float)
    for generator in generators.values():
        bus_index = bus_index_map[generator.bus_id]
        y[bus_index, 0] = generator.state.active_power_output

    data = Data(x=node_features, edge_index=edge_index, edge_attr=edge_attr, y=y)
    return data