package kafka

import (
	"encoding/json"
	"fmt"
	"time"
)

type ModbusMessage struct {
	Timestamp time.Time         `json:"timestamp"`
	DeviceID  string            `json:"deviceId"`
	Type      MeasuredValueType `json:"type"`
	Value     float64           `json:"value"`
}

type MeasuredValueType int

const (
	Voltage MeasuredValueType = iota
	Load
	Generation
	PhaseAngle
)

func (n *MeasuredValueType) UnmarshalJSON(data []byte) error {
	var s string
	if err := json.Unmarshal(data, &s); err != nil {
		return err
	}
	switch s {
	case "Voltage":
		*n = Voltage
	case "Load":
		*n = Load
	case "Generation":
		*n = Generation
	case "PhaseAngle":
		*n = PhaseAngle
	default:
		return fmt.Errorf("unknown node status: %s", s)
	}
	return nil
}
