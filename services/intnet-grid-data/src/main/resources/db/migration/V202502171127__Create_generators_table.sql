-- V202502171127__Create_generators_table.sql
CREATE TYPE fuel_type AS ENUM ('COAL', 'OIL', 'GAS', 'HYDRO');

CREATE TABLE generators (
    id SERIAL PRIMARY KEY,
    grid_id BIGINT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,

    fuel_type fuel_type,
    capacity DOUBLE PRECISION,
    operational_since TIMESTAMP WITH TIME ZONE,

    FOREIGN KEY (grid_id) REFERENCES grids(id)
);