-- V202502191056__Create_grid_element_associations_table.sql
CREATE TYPE grid_element_type AS ENUM ('SUBSTATION', 'BUS', 'TRANSFORMER', 'GENERATOR', 'LOAD', 'STORAGE', 'DER', 'TRANSMISSION_LINE', 'DISTRIBUTION_LINE');

CREATE TABLE grid_element_associations (
    id BIGSERIAL PRIMARY KEY,
    device_id BIGINT NOT NULL,
    grid_element_id BIGINT NOT NULL,
    grid_element_type grid_element_type NOT NULL
);