-- V202502171133__Create_storage_states_table.sql
CREATE TABLE storage_states (
    id BIGSERIAL PRIMARY KEY,
    grid_id BIGINT NOT NULL,
    storage_id BIGINT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    status node_status,

    charge_level DOUBLE PRECISION,
    power_output DOUBLE PRECISION,

    FOREIGN KEY (grid_id) REFERENCES grids(id),
    FOREIGN KEY (storage_id) REFERENCES storages(id)
);