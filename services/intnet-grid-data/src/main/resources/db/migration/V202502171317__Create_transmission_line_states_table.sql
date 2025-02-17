-- V202502171317__Create_transmission_line_states_table.sql
CREATE TABLE transmission_line_states (
    id BIGSERIAL PRIMARY KEY,
    grid_id BIGINT NOT NULL,
    transmission_line_id BIGINT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    current DOUBLE PRECISION, -- A
    power_flow_active DOUBLE PRECISION, -- MW
    power_flow_reactive DOUBLE PRECISION, -- MVAR
    temperature DOUBLE PRECISION, -- °C (of the line conductor)

    FOREIGN KEY (grid_id) REFERENCES grids(id),
    FOREIGN KEY (transmission_line_id) REFERENCES transmission_lines(id)
);