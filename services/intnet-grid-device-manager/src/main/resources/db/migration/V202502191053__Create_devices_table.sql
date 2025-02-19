-- V202502191053__Create_devices_table.sql
CREATE TYPE device_status AS ENUM ('ONLINE', 'FAILED', 'DISCONNECTED');

CREATE TABLE devices (
    id BIGSERIAL PRIMARY KEY,
    ip_address VARCHAR(255) NOT NULL,
    protocol VARCHAR(50) NOT NULL,
    data_structure VARCHAR(255),
    status device_status,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    latitude DECIMAL,
    longitude DECIMAL,
    metadata JSONB
);