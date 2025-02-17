-- V202502171134__Create_ders_table.sql
CREATE TYPE ders_type AS ENUM ('SOLAR', 'WIND', 'OTHER');

CREATE TABLE ders (
    id SERIAL PRIMARY KEY,
    grid_id BIGINT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,

    ders_type ders_type,
    capacity DOUBLE PRECISION,
    operational_since TIMESTAMP WITH TIME ZONE,

    FOREIGN KEY (grid_id) REFERENCES grids(id)
);