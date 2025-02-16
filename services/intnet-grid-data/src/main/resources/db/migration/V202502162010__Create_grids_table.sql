-- V202502162010__Create_grids_table.sql
CREATE TABLE grids (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    description TEXT
);