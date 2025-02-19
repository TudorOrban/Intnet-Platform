package core

import (
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
	_, handler, err := h.establishConnection(device) // Call separate connection function
	if err != nil {
		log.Printf("Error establishing connection to %s: %v", device.IPAddress, err)
		return
	}
	defer handler.Close()

}

func (h *ConnectionHandler) establishConnection(device device.Device) (modbus.Client, *modbus.TCPClientHandler, error) {
	ip := device.IPAddress
	port := 502
	address := fmt.Sprintf("%s:%d", ip, port)

	handler := modbus.NewTCPClientHandler(address)
	handler.Timeout = 10 * time.Second
	client := modbus.NewClient(handler)

	return client, handler, nil
}
