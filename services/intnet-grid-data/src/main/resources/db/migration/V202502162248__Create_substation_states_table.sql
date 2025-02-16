-- V202502162248__Create_substation_states_table.sql
CREATE TYPE node_status AS ENUM ('ACTIVE', 'FAILED', 'DISCONNECTED');

CREATE TABLE substation_states (
    id BIGSERIAL PRIMARY KEY,
    grid_id BIGINT NOT NULL,
    substation_id BIGINT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    status node_status,

    voltage DOUBLE PRECISION,
    current DOUBLE PRECISION,
    frequency DOUBLE PRECISION,
    temperature DOUBLE PRECISION,
    load DOUBLE PRECISION,
    total_inflow DOUBLE PRECISION,
    total_outflow DOUBLE PRECISION,

    FOREIGN KEY (grid_id) REFERENCES grids(id),
    FOREIGN KEY (substation_id) REFERENCES substations(id)
);