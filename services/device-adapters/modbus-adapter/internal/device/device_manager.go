package device

import (
	"fmt"
	"log"
	"time"

	"github.com/goburrow/modbus"
)

type StdDeviceManager struct {
	DeviceReader DeviceReader
}

func NewStdDeviceManager(
	deviceReader DeviceReader,
) DeviceManager {
	return &StdDeviceManager{DeviceReader: deviceReader}
}

func (m *StdDeviceManager) ConnectToDevices(devices []Device) (interface{}, error) {
	for _, device := range devices {
		go m.processDevice(device)
	}
	return nil, nil
}

func (m *StdDeviceManager) processDevice(device Device) {
	client, handler, err := m.establishConnection(device)
	if err != nil {
		log.Printf("Error establishing connection to %s: %v", device.IPAddress, err)
		return
	}
	defer handler.Close()

	go m.pollModbusValue(client, device.DeviceDataConfig.Voltage, device.IPAddress)
	go m.pollModbusValue(client, device.DeviceDataConfig.Load, device.IPAddress)
	go m.pollModbusValue(client, device.DeviceDataConfig.Generation, device.IPAddress)
	go m.pollModbusValue(client, device.DeviceDataConfig.PhaseAngle, device.IPAddress)

	select {}
}

func (m *StdDeviceManager) establishConnection(device Device) (modbus.Client, *modbus.TCPClientHandler, error) {
	ip := device.IPAddress
	port := 5020
	address := fmt.Sprintf("%s:%d", ip, port)

	handler := modbus.NewTCPClientHandler(address)
	handler.Timeout = 10 * time.Second
	client := modbus.NewClient(handler)

	return client, handler, nil
}

func (m *StdDeviceManager) pollModbusValue(client modbus.Client, reg DeviceVariableConfig, ipAddress string) {
	ticker := time.NewTicker(time.Duration(reg.PollFrequencySeconds * float64(time.Second)))
	defer ticker.Stop()

	for range ticker.C {
		value, err := m.DeviceReader.readModbusValue(client, reg)
		if err != nil {
			log.Printf("Error reading %s from %s: %v", reg.Type, ipAddress, err)
			continue
		}
		log.Printf("Read %s from %s: %v", reg.Type, ipAddress, value)
		// TODO: push to Kafka
	}
}
