-- V202502171123__Create_bus_states_table.sql
CREATE TABLE bus_states (
    id BIGSERIAL PRIMARY KEY,
    grid_id BIGINT NOT NULL,
    bus_id BIGINT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    status node_status,

    voltage DOUBLE PRECISION,
    load DOUBLE PRECISION,
    generation DOUBLE PRECISION,
    phase_angle DOUBLE PRECISION,

    FOREIGN KEY (grid_id) REFERENCES grids(id),
    FOREIGN KEY (bus_id) REFERENCES buses(id)
);