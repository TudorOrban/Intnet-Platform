-- V202502171130__Create_load_states_table.sql
CREATE TABLE load_states (
    id BIGSERIAL PRIMARY KEY,
    grid_id BIGINT NOT NULL,
    load_id BIGINT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    status node_status,

    power_demand DOUBLE PRECISION,

    FOREIGN KEY (grid_id) REFERENCES grids(id),
    FOREIGN KEY (load_id) REFERENCES loads(id)
);