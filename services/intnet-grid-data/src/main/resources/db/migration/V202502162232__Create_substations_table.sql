-- V202502162232__Create_substations_table.sql
CREATE TABLE substations (
    id SERIAL PRIMARY KEY,
    grid_id BIGINT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    capacity DOUBLE PRECISION,
    transformers INTEGER,
    connected_buses BIGINT[],
    operational_since TIMESTAMP WITH TIME ZONE,

    FOREIGN KEY (grid_id) REFERENCES grids(id)
);