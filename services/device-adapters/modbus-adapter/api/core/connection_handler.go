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

	// Read holding register
	results, err := client.ReadHoldingRegisters(1, 1)
	if err != nil {
		log.Printf("Error reading register from %s: %v", device.IPAddress, err)
		return
	}
	registerValue := binary.BigEndian.Uint16(results)
	log.Printf("Read register 1 from %s: %v", device.IPAddress, registerValue)

	// Write coil
	_, err = client.WriteSingleCoil(5, 0xFF00)
	if err != nil {
		log.Printf("Error writing to coil 5 on %s: %v", device.IPAddress, err)
		return
	}
	log.Printf("Successfully wrote to coil 5 on %s", device.IPAddress)

	// Read coil
	resultsCoil, err := client.ReadCoils(5, 1)
	if err != nil {
		log.Printf("Error reading coil 5 from %s: %v", device.IPAddress, err)
		return
	}
	coilValue := binary.BigEndian.Uint16(resultsCoil)
	log.Printf("Coil 5 value: %v", coilValue)
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
