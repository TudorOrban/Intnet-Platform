-- V202502172318__Create_bus_state_history_table.sql
CREATE TABLE bus_state_history (
    id BIGSERIAL,
    grid_id BIGINT NOT NULL,
    bus_id BIGINT NOT NULL,
    time TIMESTAMP WITH TIME ZONE NOT NULL,
    voltage DOUBLE PRECISION,
    load DOUBLE PRECISION,
    generation DOUBLE PRECISION,
    phase_angle DOUBLE PRECISION,
    PRIMARY KEY (id, time)
);

SELECT create_hypertable('bus_state_history', 'time');