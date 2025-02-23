-- V202502222154__Create_storage_units_tables.sql
CREATE TABLE storage_units (
    id BIGSERIAL PRIMARY KEY,
    grid_id BIGINT NOT NULL,
    bus_id BIGINT,
    storage_unit_name VARCHAR(255),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    storage_capacity DECIMAL,

    FOREIGN KEY (grid_id) REFERENCES grids(id),
    FOREIGN KEY (bus_id) REFERENCES buses(id)
);

CREATE INDEX idx_storage_units_grid_id ON storage_units (grid_id);
CREATE INDEX idx_storage_units_bus_id ON storage_units (bus_id);

CREATE TABLE storage_unit_states (
    id BIGSERIAL PRIMARY KEY,
    grid_id BIGINT NOT NULL,
    storage_unit_id BIGINT NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    state_of_charge DECIMAL,
    charge_discharge_rate DECIMAL,

    FOREIGN KEY (grid_id) REFERENCES grids(id),
    FOREIGN KEY (storage_unit_id) REFERENCES storage_units(id)
);

CREATE INDEX idx_storage_unit_states_grid_id ON storage_unit_states (grid_id);
CREATE INDEX idx_storage_unit_states_storage_unit_id ON storage_unit_states (storage_unit_id);
CREATE INDEX idx_storage_unit_states_updated_at ON storage_unit_states (updated_at);
