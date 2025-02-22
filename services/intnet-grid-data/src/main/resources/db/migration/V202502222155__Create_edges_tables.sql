-- V202502222155__Create_edges_tables.sql
CREATE TYPE edge_type AS ENUM ('TRANSMISSION', 'DISTRIBUTION', 'TRANSFORMER');

CREATE TABLE edges (
    id BIGSERIAL PRIMARY KEY,
    grid_id BIGINT NOT NULL,
    edge_name VARCHAR(255),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    src_bus_id BIGINT NOT NULL,
    dest_bust_id BIGINT NOT NULL,
    edge_type edge_type,

    line_length DECIMAL,
    resistance DECIMAL,
    reactance DECIMAL,
    conductance DECIMAL,
    susceptance DECIMAL,
    thermal_rating DECIMAL,
    voltage_limits_min DECIMAL,
    voltage_limits_max DECIMAL,

    FOREIGN KEY (grid_id) REFERENCES grids(id),
    FOREIGN KEY (src_bus_id) REFERENCES buses(id),
    FOREIGN KEY (dest_bus_id) REFERENCES buses(id)
);

CREATE INDEX idx_edges_grid_id ON edges (grid_id);
CREATE INDEX idx_edges_src_bus_id ON edges (src_bus_id);
CREATE INDEX idx_edges_dest_bus_id ON edges (dest_bus_id);
CREATE INDEX idx_edges_edge_type ON edges (edge_type);

CREATE TABLE edge_states (
    id BIGSERIAL PRIMARY KEY,
    edge_id BIGINT NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    line_switching_status BOOLEAN,

    FOREIGN KEY (edge_id) REFERENCES edges(id)
);

CREATE INDEX idx_edge_states_edge_id ON edge_states (edge_id);
CREATE INDEX idx_edge_states_updated_at ON edge_states (updated_at);
