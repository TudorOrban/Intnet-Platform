package core

import (
	"encoding/binary"
	"encoding/json"
	"fmt"
	"io"
	"log"
	"modbusadapter/internal/device"
	"modbusadapter/internal/kafka"
	"net/http"
	"time"

	"github.com/goburrow/modbus"
)

type ConnectionHandler struct {
	DeviceManager device.DeviceManager
	Producer      kafka.Producer
}

func NewConnectionHandler(
	deviceManager device.DeviceManager,
	producer kafka.Producer,
) *ConnectionHandler {
	return &ConnectionHandler{DeviceManager: deviceManager, Producer: producer}
}

func (h *ConnectionHandler) handleStartConnectionHandler(w http.ResponseWriter, r *http.Request) {
	body, err := io.ReadAll(r.Body)
	if err != nil {
		http.Error(w, "Failed to read request body", http.StatusBadRequest)
		return
	}
	defer r.Body.Close()

	var devices []device.Device
	if err := json.Unmarshal(body, &devices); err != nil {
		http.Error(w, "Failed to unmarshal device list", http.StatusBadRequest)
		return
	}

	log.Printf("Received devices: %+v", devices)

	for _, device := range devices {
		go h.processDevice(device)
	}

	w.WriteHeader(http.StatusOK)
}

func (h *ConnectionHandler) processDevice(device device.Device) {
	client, handler, err := h.establishConnection(device) // Call separate connection function
	if err != nil {
		log.Printf("Error establishing connection to %s: %v", device.IPAddress, err)
		return
	}
	defer handler.Close()

	go h.pollModbusValue(client, device.DeviceDataConfig.Voltage, device.IPAddress)
	go h.pollModbusValue(client, device.DeviceDataConfig.Load, device.IPAddress)
	go h.pollModbusValue(client, device.DeviceDataConfig.Generation, device.IPAddress)
	go h.pollModbusValue(client, device.DeviceDataConfig.PhaseAngle, device.IPAddress)

	select {}
}

func (h *ConnectionHandler) establishConnection(device device.Device) (modbus.Client, *modbus.TCPClientHandler, error) {
	ip := device.IPAddress
	port := 5020
	address := fmt.Sprintf("%s:%d", ip, port)

	handler := modbus.NewTCPClientHandler(address)
	handler.Timeout = 10 * time.Second
	client := modbus.NewClient(handler)

	return client, handler, nil
}

func (h *ConnectionHandler) pollModbusValue(client modbus.Client, reg device.DeviceVariableConfig, ipAddress string) {
	ticker := time.NewTicker(time.Duration(reg.PollFrequencySeconds * float64(time.Second)))
	defer ticker.Stop()

	for range ticker.C {
		value, err := h.readModbusValue(client, reg)
		if err != nil {
			log.Printf("Error reading %s from %s: %v", reg.Type, ipAddress, err)
			continue
		}
		log.Printf("Read %s from %s: %v", reg.Type, ipAddress, value)
		// TODO: push to Kafka
	}
}

func (h *ConnectionHandler) readModbusValue(client modbus.Client, reg device.DeviceVariableConfig) (interface{}, error) {
	switch reg.Type {
	case "holdingRegister":
		return h.readHoldingRegister(client, reg)
	case "inputRegister":
		return h.readInputRegister(client, reg)
	case "coil":
		return h.readCoil(client, reg)
	case "discreteInput":
		return h.readDiscreteInput(client, reg)
	default:
		return nil, fmt.Errorf("unsupported register type: %s", reg.Type)
	}
}

func (h *ConnectionHandler) readHoldingRegister(client modbus.Client, reg device.DeviceVariableConfig) (interface{}, error) {
	results, err := client.ReadHoldingRegisters(reg.Address, 1)
	if err != nil {
		return nil, err
	}
	if len(results) < 2 {
		return nil, fmt.Errorf("ReadHoldingRegisters returned less than 2 bytes")
	}
	val := binary.BigEndian.Uint16(results[:2])
	return float64(val) * reg.ScalingFactor, nil
}

func (h *ConnectionHandler) readInputRegister(client modbus.Client, reg device.DeviceVariableConfig) (interface{}, error) {
	results, err := client.ReadInputRegisters(reg.Address, 1)
	if err != nil {
		return nil, err
	}
	if len(results) < 2 {
		return nil, fmt.Errorf("ReadInputRegisters returned less than 2 bytes")
	}
	val := binary.BigEndian.Uint16(results[:2])
	return float64(val) * reg.ScalingFactor, nil
}

func (h *ConnectionHandler) readCoil(client modbus.Client, reg device.DeviceVariableConfig) (interface{}, error) {
	results, err := client.ReadCoils(reg.Address, 1)
	if err != nil {
		return nil, err
	}
	if len(results) < 1 {
		return nil, fmt.Errorf("ReadCoils returned less than 1 byte")
	}
	return results[0], nil // Coil value is a single byte (0 or 1)
}

func (h *ConnectionHandler) readDiscreteInput(client modbus.Client, reg device.DeviceVariableConfig) (interface{}, error) {
	results, err := client.ReadDiscreteInputs(reg.Address, 1)
	if err != nil {
		return nil, err
	}
	if len(results) < 1 {
		return nil, fmt.Errorf("ReadDiscreteInputs returned less than 1 byte")
	}
	return results[0], nil // Discrete input value is a single byte (0 or 1)
}

// // Read holding register
// results, err := client.ReadHoldingRegisters(1, 1)
// if err != nil {
// 	log.Printf("Error reading register from %s: %v", device.IPAddress, err)
// 	return
// }
// registerValue := binary.BigEndian.Uint16(results)
// log.Printf("Read register 1 from %s: %v", device.IPAddress, registerValue)

// // Write coil
// _, err = client.WriteSingleCoil(5, 0xFF00)
// if err != nil {
// 	log.Printf("Error writing to coil 5 on %s: %v", device.IPAddress, err)
// 	return
// }
// log.Printf("Successfully wrote to coil 5 on %s", device.IPAddress)

// // Read coil
// resultsCoil, err := client.ReadCoils(5, 1)
// if err != nil {
// 	log.Printf("Error reading coil 5 from %s: %v", device.IPAddress, err)
// 	return
// }
// if len(resultsCoil) < 1 {
// 	log.Printf("Error: ReadCoils returned less than 1 byte")
// 	return
// }

// coilValue := resultsCoil[0]
// log.Printf("Coil 5 value: %v", coilValue)
