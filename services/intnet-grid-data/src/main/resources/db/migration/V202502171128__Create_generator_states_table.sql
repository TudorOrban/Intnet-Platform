-- V202502171128__Create_generator_states_table.sql
CREATE TABLE generator_states (
    id BIGSERIAL PRIMARY KEY,
    grid_id BIGINT NOT NULL,
    generator_id BIGINT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    status node_status,

    power_output DOUBLE PRECISION,
    reactive_power DOUBLE PRECISION,

    FOREIGN KEY (grid_id) REFERENCES grids(id),
    FOREIGN KEY (generator_id) REFERENCES generators(id)
);