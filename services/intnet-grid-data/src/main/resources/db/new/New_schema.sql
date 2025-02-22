-- Nodes
-- Edges
-- Devices
CREATE TYPE measurement_target_type AS ENUM ('BUS', 'EDGE', 'GENERATOR', 'LOAD', 'DER', 'STORAGE_UNIT');

CREATE TYPE device_status AS ENUM ('ONLINE', 'FAILED', 'DISCONNECTED');

CREATE TABLE devices (
    id BIGSERIAL PRIMARY KEY,
    grid_id BIGINT NOT NULL,
    target_id BIGINT,
    target_type measurement_target_type,
    device_name VARCHAR(255),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    device_metadata TEXT,

    ip_address VARCHAR(255),
    protocol VARCHAR(50),
    device_config TEXT,
    status device_status,
    last_communication_time TIMESTAMP WITH TIME ZONE,

    FOREIGN KEY (grid_id) REFERENCES grids(id)
);

CREATE INDEX idx_devices_grid_id ON devices (grid_id);
CREATE INDEX idx_devices_target ON devices (target_id, target_type);