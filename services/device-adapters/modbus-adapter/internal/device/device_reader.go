package device

import (
	"encoding/binary"
	"fmt"

	"github.com/goburrow/modbus"
)

type StdDeviceReader struct{}

func NewStdDeviceReader() DeviceReader {
	return &StdDeviceReader{}
}

func (r *StdDeviceReader) readModbusValue(client modbus.Client, reg DeviceVariableConfig) (float64, error) {
	switch reg.Type {
	case "HOLDING_REGISTER":
		return r.readHoldingRegister(client, reg)
	case "INPUT_REGISTER":
		return r.readInputRegister(client, reg)
	default:
		return -1, fmt.Errorf("unsupported register type: %s", reg.Type)
	}
}

func (r *StdDeviceReader) readHoldingRegister(client modbus.Client, reg DeviceVariableConfig) (float64, error) {
	results, err := client.ReadHoldingRegisters(reg.Address, 1)
	if err != nil {
		return 0, err
	}
	if len(results) < 2 {
		return 0, fmt.Errorf("ReadHoldingRegisters returned less than 2 bytes")
	}
	val := binary.BigEndian.Uint16(results[:2])
	return float64(val) * reg.ScalingFactor, nil
}

func (r *StdDeviceReader) readInputRegister(client modbus.Client, reg DeviceVariableConfig) (float64, error) {
	results, err := client.ReadInputRegisters(reg.Address, 1)
	if err != nil {
		return 0, err
	}
	if len(results) < 2 {
		return 0, fmt.Errorf("ReadInputRegisters returned less than 2 bytes")
	}
	val := binary.BigEndian.Uint16(results[:2])
	return float64(val) * reg.ScalingFactor, nil
}

func (r *StdDeviceReader) readCoil(client modbus.Client, reg DeviceVariableConfig) (byte, error) {
	results, err := client.ReadCoils(reg.Address, 1)
	if err != nil {
		return 0, err
	}
	if len(results) < 1 {
		return 0, fmt.Errorf("ReadCoils returned less than 1 byte")
	}
	return results[0], nil
}

func (r *StdDeviceReader) readDiscreteInput(client modbus.Client, reg DeviceVariableConfig) (byte, error) {
	results, err := client.ReadDiscreteInputs(reg.Address, 1)
	if err != nil {
		return 0, err
	}
	if len(results) < 1 {
		return 0, fmt.Errorf("ReadDiscreteInputs returned less than 1 byte")
	}
	return results[0], nil
}
