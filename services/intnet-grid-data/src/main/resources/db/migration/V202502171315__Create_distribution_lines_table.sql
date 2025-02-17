-- V202502171315__Create_distribution_lines_table.sql
CREATE TABLE distribution_lines (
    id BIGSERIAL PRIMARY KEY,
    grid_id BIGINT NOT NULL,
    source_node_id BIGINT,
    source_node_type node_type,
    destination_node_id BIGINT,
    destination_node_type node_type,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    line_type line_type,
    length DOUBLE PRECISION,  -- km
    impedance DOUBLE PRECISION, -- Ω/km
    admittance DOUBLE PRECISION, -- S/km
    capacity DOUBLE PRECISION,  -- MVA

    FOREIGN KEY (grid_id) REFERENCES grids(id)
);