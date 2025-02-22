-- V202502222152__Create_loads_tables.sql
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
