-- V202502222151__Create_generators_tables.sql
CREATE TABLE generators (
    id BIGSERIAL PRIMARY KEY,
    grid_id BIGINT NOT NULL,
    bus_id BIGINT NOT NULL,
    generator_name VARCHAR(255),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    generator_voltage_setpoint DECIMAL,
    generator_max_active_power DECIMAL,
    generator_min_active_power DECIMAL,
    generator_max_reactive_power DECIMAL,
    generator_min_reactive_power DECIMAL,

    FOREIGN KEY (grid_id) REFERENCES grids(id),
    FOREIGN KEY (bus_id) REFERENCES buses(id)
);

CREATE INDEX idx_generators_grid_id ON generators (grid_id);
CREATE INDEX idx_generators_bus_id ON generators (bus_id);

CREATE TABLE generator_states (
    id BIGSERIAL PRIMARY KEY,
    grid_id BIGINT NOT NULL,
    generator_id BIGINT NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    active_power_generation DECIMAL,
    reactive_power_generation DECIMAL,
    generator_voltage_setpoint DECIMAL,

    FOREIGN KEY (grid_id) REFERENCES grids(id),
    FOREIGN KEY (generator_id) REFERENCES generators(id)
);

CREATE INDEX idx_generator_states_grid_id ON generator_states (grid_id);
CREATE INDEX idx_generator_states_generator_id ON generator_states (generator_id);
CREATE INDEX idx_generator_states_updated_at ON generator_states (updated_at);
