-- V202502171136__Create_der_states_table.sql
CREATE TABLE der_states (
    id BIGSERIAL PRIMARY KEY,
    grid_id BIGINT NOT NULL,
    der_id BIGINT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    status node_status,

    power_output DOUBLE PRECISION,

    FOREIGN KEY (grid_id) REFERENCES grids(id),
    FOREIGN KEY (der_id) REFERENCES ders(id)
);