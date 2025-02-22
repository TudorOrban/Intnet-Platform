-- V202502222153__Create_ders_tables.sql
CREATE TYPE der_type AS ENUM ('SOLAR', 'WIND');

CREATE TABLE ders (
    id BIGSERIAL PRIMARY KEY,
    bus_id BIGINT,
    der_name VARCHAR(255),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    der_type der_type,

    FOREIGN KEY (bus_id) REFERENCES buses(id)
);

CREATE INDEX idx_ders_bus_id ON ders (bus_id);

CREATE TABLE der_states (
    id BIGSERIAL PRIMARY KEY,
    der_id BIGINT NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    active_power_der DECIMAL,
    reactive_power_der DECIMAL,

    FOREIGN KEY (der_id) REFERENCES ders(id)
);

CREATE INDEX idx_der_states_der_id ON der_states (der_id);
CREATE INDEX idx_der_states_updated_at ON der_states (updated_at);
