-- V202502222156__Create_transformers_tables.sql
CREATE TABLE transformers (
    id BIGSERIAL PRIMARY KEY,
    grid_id BIGINT NOT NULL,
    edge_id BIGINT NOT NULL,

    turns_ratio DECIMAL,
    impedance DECIMAL,

    FOREIGN KEY (grid_id) REFERENCES grids(id),
    FOREIGN KEY (edge_id) REFERENCES edges(id)
);

CREATE INDEX idx_transformers_grid_id ON transformers (grid_id);
CREATE INDEX idx_transformers_edge_id ON transformers (edge_id);

CREATE TABLE transformer_states (
    id BIGSERIAL PRIMARY KEY,
    grid_id BIGINT NOT NULL,
    transformer_id BIGINT NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    tap_position DECIMAL,

    FOREIGN KEY (grid_id) REFERENCES grids(id),
    FOREIGN KEY (transformer_id) REFERENCES transformers(id)
);

CREATE INDEX idx_transformer_states_grid_id ON transformer_states (grid_id);
CREATE INDEX idx_transformer_states_transformer_id ON transformer_states (transformer_id);
CREATE INDEX idx_transformer_states_updated_at ON transformer_states (updated_at);
