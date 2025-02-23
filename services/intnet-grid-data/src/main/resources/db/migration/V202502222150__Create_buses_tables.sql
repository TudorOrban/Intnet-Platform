-- V202502222150__Create_buses_tables.sql
CREATE TYPE bus_type AS ENUM ('PQ', 'PV', 'SLACK');

CREATE TABLE buses (
    id BIGSERIAL PRIMARY KEY,
    grid_id BIGINT NOT NULL,
    bus_name VARCHAR(255),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    bus_type bus_type,
    latitude DECIMAL,
    longitude DECIMAL,

    FOREIGN KEY (grid_id) REFERENCES grids(id)
);

CREATE INDEX idx_buses_grid_id ON buses (grid_id);
CREATE INDEX idx_buses_bus_type ON buses (bus_type);

CREATE TABLE bus_states (
    id BIGSERIAL PRIMARY KEY,
    grid_id BIGINT NOT NULL,
    bus_id BIGINT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    voltage_magnitude DECIMAL,
    voltage_angle DECIMAL,
    active_power_injection DECIMAL,
    reactive_power_injection DECIMAL,
    shunt_capacitor_reactor_status BOOLEAN,
    phase_shifting_transformer_tap_position DECIMAL,

    FOREIGN KEY (grid_id) REFERENCES grids(id),
    FOREIGN KEY (bus_id) REFERENCES buses(id)
);

CREATE INDEX idx_bus_states_grid_id ON bus_states (grid_id);
CREATE INDEX idx_bus_states_bus_id ON bus_states (bus_id);
CREATE INDEX idx_bus_states_updated_at ON bus_states (updated_at);
