-- V202502221152__Alter_bus_state_history_table.sql
SELECT drop_hypertable('bus_state_history', if_exists => true);

SELECT create_hypertable('bus_state_history', 'time', partitioning_column => 'grid_id, bus_id');

CREATE INDEX ON bus_state_history (grid_id, bus_id, time DESC);