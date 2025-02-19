-- V202502191523__Update_devices_table.sql
ALTER TABLE devices
DROP COLUMN metadata;

ALTER TABLE devices
ADD COLUMN metadata TEXT;