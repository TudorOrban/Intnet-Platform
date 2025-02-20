package device

import (
	"fmt"
	"log"
	"modbusadapter/internal/kafka"
	"time"

	"github.com/goburrow/modbus"
)

type StdDeviceManager struct {
	DeviceReader DeviceReader
	Producer     kafka.Producer
}

func NewStdDeviceManager(
	deviceReader DeviceReader,
	producer kafka.Producer,
) DeviceManager {
	return &StdDeviceManager{DeviceReader: deviceReader, Producer: producer}
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

	go m.pollModbusValue(client, device.DeviceDataConfig.Voltage, device.IPAddress, device.ID)
	go m.pollModbusValue(client, device.DeviceDataConfig.Load, device.IPAddress, device.ID)
	go m.pollModbusValue(client, device.DeviceDataConfig.Generation, device.IPAddress, device.ID)
	go m.pollModbusValue(client, device.DeviceDataConfig.PhaseAngle, device.IPAddress, device.ID)

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

func (m *StdDeviceManager) pollModbusValue(client modbus.Client, reg DeviceVariableConfig, ipAddress string, deviceID string) {
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
		message := kafka.ModbusMessage{
			Timestamp: time.Now(),
			DeviceID:  deviceID,
			Type:      kafka.GetMeasuredValueType(reg.Type),
			Value:     value,
		}

		err = m.Producer.Produce(message)
		if err != nil {
			log.Printf("Error pushing to Kafka: %v", err)
			continue
		}

		log.Printf("Pushed %s from %s to Kafka: %v", reg.Type, ipAddress, value)
	}
}
