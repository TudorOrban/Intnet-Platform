package core

import (
	"encoding/json"
	"io"
	"log"
	"modbusadapter/internal/device"
	"net/http"
)

type ConnectionHandler struct {
	DeviceManager device.DeviceManager
}

func NewConnectionHandler(
	deviceManager device.DeviceManager,
) *ConnectionHandler {
	return &ConnectionHandler{DeviceManager: deviceManager}
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

	h.DeviceManager.ConnectToDevices(devices)

	w.WriteHeader(http.StatusOK)
}
