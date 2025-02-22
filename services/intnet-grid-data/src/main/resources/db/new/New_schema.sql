-- Nodes
CREATE TYPE bus_type AS ENUM ('PQ', 'PV', 'SLACK');

CREATE TABLE buses (
    id BIGSERIAL PRIMARY KEY,
    bus_name VARCHAR(255),
    grid_id BIGINT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    latitude DECIMAL,
    longitude DECIMAL,

    FOREIGN KEY (grid_id) REFERENCES grids(id)
);

CREATE INDEX idx_buses_grid_id ON buses (grid_id);
CREATE INDEX idx_buses_bus_type ON buses (bus_type);

CREATE TABLE bus_states (
    id BIGSERIAL PRIMARY KEY,
    bus_id BIGINT NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    voltage_magnitude DECIMAL,
    voltage_angle DECIMAL,
    active_power_injection DECIMAL,
    reactive_power_injection DECIMAL,
    shunt_capacitor_reactor_status BOOLEAN,
    phase_shifting_transformer_tap_position DECIMAL,

    FOREIGN KEY (bus_id) REFERENCES buses(id)
);

CREATE INDEX idx_bus_states_bus_id ON bus_states (bus_id);
CREATE INDEX idx_bus_states_updated_at ON bus_states (updated_at);

CREATE TABLE generators (
    id BIGSERIAL PRIMARY KEY,
    bus_id BIGINT NOT NULL,
    generator_name VARCHAR(255),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    generator_voltage_setpoint DECIMAL,
    generator_max_active_power DECIMAL,
    generator_min_active_power DECIMAL,
    generator_max_reactive_power DECIMAL,
    generator_min_reactive_power DECIMAL,

    FOREIGN KEY (bus_id) REFERENCES buses(id)
);

CREATE INDEX idx_generators_bus_id ON generators (bus_id);

CREATE TABLE generator_states (
    id BIGSERIAL PRIMARY KEY,
    generator_id BIGINT NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    active_power_generation DECIMAL,
    reactive_power_generation DECIMAL,
    generator_voltage_setpoint DECIMAL,

    FOREIGN KEY (generator_id) REFERENCES generators(id)
);

CREATE INDEX idx_generator_states_generator_id ON generator_states (generator_id);
CREATE INDEX idx_generator_states_updated_at ON generator_states (updated_at);

CREATE TYPE load_type AS ENUM ('RESIDENTIAL', 'COMMERCIAL', 'INDUSTRIAL');

CREATE TABLE loads (
    id BIGSERIAL PRIMARY KEY,
    bus_id BIGINT NOT NULL,
    load_name VARCHAR(255),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    load_type load_type,

    FOREIGN KEY (bus_id) REFERENCES buses(id)
);

CREATE INDEX idx_loads_bus_id ON loads (bus_id);

CREATE TABLE load_states (
    id BIGSERIAL PRIMARY KEY,
    load_id BIGINT NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    active_power_load DECIMAL,
    reactive_power_load DECIMAL,

    FOREIGN KEY (load_id) REFERENCES loads(id)
);

CREATE INDEX idx_load_states_load_id ON load_states (load_id);
CREATE INDEX idx_load_states_updated_at ON load_states (updated_at);

CREATE TYPE der_type AS ENUM ('SOLAR', 'WIND');

CREATE TABLE ders (
    id BIGSERIAL PRIMARY KEY,
    bus_id BIGINT,
    der_name VARCHAR(255),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    der_type der_type,

    FOREIGN KEY (bus_id) REFERENCES buses(id)
);

CREATE INDEX idx_ders_bus_id ON ders (bus_id);

CREATE TABLE der_states (
    id BIGSERIAL PRIMARY KEY,
    der_id BIGINT NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    active_power_der DECIMAL,
    reactive_power_der DECIMAL,

    FOREIGN KEY (der_id) REFERENCES ders(id)
);

CREATE INDEX idx_der_states_der_id ON der_states (der_id);
CREATE INDEX idx_der_states_updated_at ON der_states (updated_at);

CREATE TABLE storage_units (
    id BIGSERIAL PRIMARY KEY,
    bus_id BIGINT,
    storage_unit_name VARCHAR(255),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    storage_capacity DECIMAL,

    FOREIGN KEY (bus_id) REFERENCES buses(id)
);

CREATE INDEX idx_storage_units_bus_id ON storage_units (bus_id);

CREATE TABLE storage_unit_states (
    id BIGSERIAL PRIMARY KEY,
    storage_unit_id BIGINT NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    state_of_charge DECIMAL,
    charge_discharge_rate DECIMAL,

    FOREIGN KEY (storage_unit_id) REFERENCES storage_units(id)
);

CREATE INDEX idx_storage_unit_states_storage_unit_id ON storage_unit_states (storage_unit_id);
CREATE INDEX idx_storage_unit_states_updated_at ON storage_unit_states (updated_at);

-- Edges
CREATE TYPE edge_type AS ENUM ('TRANSMISSION', 'DISTRIBUTION', 'TRANSFORMER');

CREATE TABLE edges (
    id BIGSERIAL PRIMARY KEY,
    grid_id BIGINT NOT NULL,
    edge_name VARCHAR(255),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    src_bus_id BIGINT NOT NULL,
    dest_bust_id BIGINT NOT NULL,
    edge_type edge_type,

    line_length DECIMAL,
    resistance DECIMAL,
    reactance DECIMAL,
    conductance DECIMAL,
    susceptance DECIMAL,
    thermal_rating DECIMAL,
    voltage_limits_min DECIMAL,
    voltage_limits_max DECIMAL,

    FOREIGN KEY (grid_id) REFERENCES grids(id),
    FOREIGN KEY (src_bus_id) REFERENCES buses(id),
    FOREIGN KEY (dest_bus_id) REFERENCES buses(id)
);

CREATE INDEX idx_edges_grid_id ON edges (grid_id);
CREATE INDEX idx_edges_src_bus_id ON edges (src_bus_id);
CREATE INDEX idx_edges_dest_bus_id ON edges (dest_bus_id);
CREATE INDEX idx_edges_edge_type ON edges (edge_type);

CREATE TABLE edge_states (
    id BIGSERIAL PRIMARY KEY,
    edge_id BIGINT NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    line_switching_status BOOLEAN,

    FOREIGN KEY (edge_id) REFERENCES edges(id)
);

CREATE INDEX idx_edge_states_edge_id ON edge_states (edge_id);
CREATE INDEX idx_edge_states_updated_at ON edge_states (updated_at);

CREATE TABLE transformers (
    id BIGSERIAL PRIMARY KEY,
    edge_id BIGINT NOT NULL,
    turns_ratio DECIMAL,
    impedance DECIMAL,
    FOREIGN KEY (edge_id) REFERENCES edges(id)
);

CREATE INDEX idx_transformers_edge_id ON transformers (edge_id);

CREATE TABLE transformer_states (
    id BIGSERIAL PRIMARY KEY,
    transformer_id BIGINT NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    tap_position DECIMAL,

    FOREIGN KEY (transformer_id) REFERENCES transformers(id)
);

CREATE INDEX idx_transformer_states_transformer_id ON transformer_states (transformer_id);
CREATE INDEX idx_transformer_states_updated_at ON transformer_states (updated_at);

-- Devices
CREATE TYPE measurement_target_type AS ENUM ('BUS', 'EDGE', 'GENERATOR', 'LOAD', 'DER', 'STORAGE_UNIT');

CREATE TYPE device_status AS ENUM ('ONLINE', 'FAILED', 'DISCONNECTED');

CREATE TABLE devices (
    id BIGSERIAL PRIMARY KEY,
    grid_id BIGINT NOT NULL,
    target_id BIGINT,
    target_type measurement_target_type,
    device_name VARCHAR(255),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    device_metadata TEXT,

    ip_address VARCHAR(255),
    protocol VARCHAR(50),
    device_config TEXT,
    status device_status,
    last_communication_time TIMESTAMP WITH TIME ZONE,

    FOREIGN KEY (grid_id) REFERENCES grids(id)
);

CREATE INDEX idx_devices_grid_id ON devices (grid_id);
CREATE INDEX idx_devices_target ON devices (target_id, target_type);