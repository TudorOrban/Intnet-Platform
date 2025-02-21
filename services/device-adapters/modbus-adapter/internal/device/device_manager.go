package device

import (
	"fmt"
	"log"
	"modbusadapter/internal/kafka"
	"sync"
	"time"

	"github.com/goburrow/modbus"
)

type StdDeviceManager struct {
	DeviceReader DeviceReader
	Producer     kafka.Producer
	tickers      sync.Map
	stopChans    sync.Map
}

func NewStdDeviceManager(
	deviceReader DeviceReader,
	producer kafka.Producer,
) DeviceManager {
	return &StdDeviceManager{
		DeviceReader: deviceReader,
		Producer:     producer,
		tickers:      sync.Map{},
		stopChans:    sync.Map{},
	}
}

func (m *StdDeviceManager) ConnectToDevices(devices []Device) (interface{}, error) {
	for _, device := range devices {
		go m.startDeviceProcessing(device)
	}
	return nil, nil
}

func (m *StdDeviceManager) startDeviceProcessing(device Device) {
	client, handler, err := m.establishConnection(device)
	if err != nil {
		log.Printf("Error establishing connection to %s: %v", device.IPAddress, err)
		return
	}

	stopChan := make(chan struct{})
	m.stopChans.Store(device.ID, stopChan)

	m.pollModbusValue(client, handler, device.DeviceDataConfig.Voltage, device.IPAddress, device.ID, stopChan)
	m.pollModbusValue(client, handler, device.DeviceDataConfig.Load, device.IPAddress, device.ID, stopChan)
	m.pollModbusValue(client, handler, device.DeviceDataConfig.Generation, device.IPAddress, device.ID, stopChan)
	m.pollModbusValue(client, handler, device.DeviceDataConfig.PhaseAngle, device.IPAddress, device.ID, stopChan)
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

func (m *StdDeviceManager) pollModbusValue(
	client modbus.Client,
	handler *modbus.TCPClientHandler,
	reg DeviceVariableConfig,
	ipAddress string,
	deviceID int64,
	stopChan chan struct{},
) {
	ticker := time.NewTicker(time.Duration(reg.PollFrequencySeconds * float64(time.Second)))
	m.tickers.Store(deviceID, ticker)
	defer ticker.Stop()

	for range ticker.C {
		select {
		case <-ticker.C:
			m.pushToKafka(client, reg, ipAddress, deviceID)
		case <-stopChan:
			log.Printf("Stopping polling for device %d (%s)", deviceID, ipAddress)
			handler.Close()
			return
		}
	}
}

func (m *StdDeviceManager) pushToKafka(
	client modbus.Client,
	reg DeviceVariableConfig,
	ipAddress string,
	deviceID int64,
) {
	value, err := m.DeviceReader.readModbusValue(client, reg)
	if err != nil {
		log.Printf("Error reading %s from %s: %v", reg.Type, ipAddress, err)
		return
	}
	log.Printf("Read %s from %s: %v", reg.Type, ipAddress, value)

	message := kafka.ModbusMessage{
		Timestamp: time.Now(),
		DeviceID:  deviceID,
		Type:      kafka.GetMeasuredValueType(reg.Type),
		Value:     value,
	}

	err = m.Producer.Produce(message)
	if err != nil {
		log.Printf("Error pushing to Kafka: %v", err)
		return
	}

	log.Printf("Pushed %s from %s to Kafka: %v", reg.Type, ipAddress, value)
}

func (m *StdDeviceManager) StopConnection() {
	m.stopChans.Range(func(key, value interface{}) bool {
		deviceID := key.(string)
		stopChan := value.(chan struct{})
		close(stopChan)
		m.stopChans.Delete(key)
		m.tickers.Delete(key)
		log.Printf("Stopping connection for device %s", deviceID)
		return true
	})
}
