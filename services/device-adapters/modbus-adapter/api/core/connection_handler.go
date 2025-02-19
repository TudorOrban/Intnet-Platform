package core

import (
	"io"
	"log"
	"modbusadapter/internal/device"
	"modbusadapter/internal/kafka"
	"net/http"
)

type ConnectionHandler struct {
	DeviceManager device.DeviceManager
	Producer kafka.Producer
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

	log.Printf("Body: %s", body)

	w.WriteHeader(http.StatusOK)
}