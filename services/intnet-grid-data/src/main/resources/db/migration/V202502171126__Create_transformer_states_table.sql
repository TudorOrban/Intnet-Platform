-- V202502171126__Create_transformer_states_table.sql
CREATE TABLE transformer_states (
    id BIGSERIAL PRIMARY KEY,
    grid_id BIGINT NOT NULL,
    transformer_id BIGINT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    status node_status,

    tap_position INTEGER,
    temperature DOUBLE PRECISION,

    FOREIGN KEY (grid_id) REFERENCES grids(id),
    FOREIGN KEY (transformer_id) REFERENCES transformers(id)
);