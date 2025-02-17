-- V202502171124__Create_transformers_table.sql
CREATE TABLE transformers (
    id SERIAL PRIMARY KEY,
    grid_id BIGINT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,

    capacity DOUBLE PRECISION,
    voltage_ratio VARCHAR(255),
    impedance DOUBLE PRECISION,
    operational_since TIMESTAMP WITH TIME ZONE,

    FOREIGN KEY (grid_id) REFERENCES grids(id)
);