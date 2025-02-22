-- V202502221503__Create_buses_table.sql
CREATE TYPE bus_type AS ENUM ('PQ', 'PV', 'SLACK');

CREATE TABLE buses (
    id BIGSERIAL PRIMARY KEY,
    bus_name VARCHAR(255),
    grid_id BIGINT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    voltage_magnitude DECIMAL,
    voltage_angle DECIMAL,
    active_power_injection DECIMAL,
    reactive_power_injection DECIMAL,
    bus_type bus_type,

    shunt_capacitor_reactor_status BOOLEAN,
    phase_shifting_transformer_tap_position DECIMAL,

    latitude DECIMAL,
    longitude DECIMAL,

    FOREIGN KEY (grid_id) REFERENCES grids(id)
);

CREATE INDEX idx_buses_grid_id ON buses (grid_id);
CREATE INDEX idx_buses_bus_type ON buses (bus_type);

CREATE TABLE generators (
    id BIGSERIAL PRIMARY KEY,
    bus_id BIGINT NOT NULL,
    generator_name VARCHAR(255),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    active_power_generation DECIMAL,
    reactive_power_generation DECIMAL,
    generator_type VARCHAR(50),
    generator_voltage_setpoint DECIMAL,
    generator_max_active_power DECIMAL,
    generator_min_active_power DECIMAL,
    generator_max_reactive_power DECIMAL,
    generator_min_reactive_power DECIMAL,

    FOREIGN KEY (bus_id) REFERENCES buses(id)
);

CREATE TYPE load_type AS ENUM ('RESIDENTIAL', 'COMMERCIAL', 'INDUSTRIAL');

CREATE TABLE loads (
    id BIGSERIAL PRIMARY KEY,
    bus_id BIGINT NOT NULL,
    load_name VARCHAR(255),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    active_power_load DECIMAL,
    reactive_power_load DECIMAL,
    load_type load_type,

    FOREIGN KEY (bus_id) REFERENCES buses(id)
);

CREATE INDEX idx_loads_bus_id ON loads (bus_id);
CREATE INDEX idx_generators_bus_id ON generators (bus_id);

CREATE TYPE der_type AS ENUM ('SOLAR', 'WIND');

CREATE TABLE ders (
    id BIGSERIAL PRIMARY KEY,
    bus_id BIGINT,
    der_name VARCHAR(255),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    der_type der_type,
    active_power_der DECIMAL,
    reactive_power_der DECIMAL,

    FOREIGN KEY (bus_id) REFERENCES buses(id)
);

CREATE INDEX idx_ders_bus_id ON ders (bus_id);

CREATE TABLE storage_units (
    id BIGSERIAL PRIMARY KEY,
    bus_id BIGINT,
    storage_unit_name VARCHAR(255),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    state_of_charge DECIMAL,
    charge_discharge_rate DECIMAL,
    storage_capacity DECIMAL,

    FOREIGN KEY (bus_id) REFERENCES buses(id)
);

CREATE INDEX idx_storage_units_bus_id ON storage_units (bus_id);

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
    line_switching_status BOOLEAN,

    FOREIGN KEY (grid_id) REFERENCES grids(id),
    FOREIGN KEY (src_bus_id) REFERENCES buses(id),
    FOREIGN KEY (dest_bus_id) REFERENCES buses(id)
);

CREATE INDEX idx_edges_grid_id ON edges (grid_id);
CREATE INDEX idx_edges_src_bus_id ON edges (src_bus_id);
CREATE INDEX idx_edges_dest_bus_id ON edges (dest_bus_id);
CREATE INDEX idx_edges_edge_type ON edges (edge_type);

CREATE TABLE devices (
    id BIGSERIAL PRIMARY KEY,
    grid_id BIGINT NOT NULL,
    device_name VARCHAR(255),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    device_metadata TEXT,

    ip_address VARCHAR(255),
    protocol VARCHAR(50),
    device_type VARCHAR(50),

    FOREIGN KEY (grid_id) REFERENCES grids(id)
);

CREATE TYPE measurement_target_type AS ENUM ('BUS', 'EDGE', 'GENERATOR', 'LOAD', 'DER', 'STORAGE_UNIT');

CREATE TABLE device_measurements (

);