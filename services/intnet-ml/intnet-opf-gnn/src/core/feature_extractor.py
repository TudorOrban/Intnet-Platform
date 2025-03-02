import torch
from core.data_types import BusData, GeneratorData, TransmissionLineData


def extract_bus_features(bus: BusData) -> torch.Tensor:
    """Extracts node features from a BusData object."""
    state = bus.state
    features = torch.tensor([
        bus.latitude,
        bus.longitude,
        state.voltage_magnitude,
        state.voltage_angle,
        state.active_power_injection,
        state.reactive_power_injection,
        float(state.shunt_capacitor_reactor_status),
        state.phase_shifting_transformer_tap_position if state.phase_shifting_transformer_tap_position is not None else 0.0,
    ], dtype=torch.float)
    return features

def extract_generator_features(generator: GeneratorData) -> torch.Tensor:
    """Extracts features from a GeneratorData object."""
    state = generator.state
    features = torch.tensor([
        generator.generator_max_active_power,
        generator.generator_min_active_power,
        generator.generator_max_reactive_power,
        generator.generator_min_reactive_power,
        state.active_power_output,
        state.reactive_power_output,
    ], dtype=torch.float)
    return features

def extract_line_features(line: TransmissionLineData) -> torch.Tensor:
    """Extracts edge features from a TransmissionLineData object."""
    state = line.state
    features = torch.tensor([
        line.resistance,
        line.reactance,
        line.susceptance,
        line.line_length,
        line.max_flow,
        state.active_power_flow,
        state.reactive_power_flow,
        state.current_magnitude,
        float(state.line_status),
    ], dtype=torch.float)
    return features