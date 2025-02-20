package device

import "github.com/goburrow/modbus"

type DeviceManager interface {
	ConnectToDevices(devices []Device) (interface{}, error)
}

type DeviceReader interface {
	readModbusValue(client modbus.Client, reg DeviceVariableConfig) (float64, error)
}