package device

type Device struct {
	ID            string         `json:"id"`
	IPAddress     string         `json:"ipAddress"`
	Protocol      string         `json:"protocol"`
	DataStructure string         `json:"dataStructure"`
	DeviceMapping DeviceMapping  `json:"deviceMapping"`
	Latitude      float32        `json:"latitude"`
	Longitude     float32        `json:"longitude"`
	Metadata      DeviceMetadata `json:"metadata"`
}

type DeviceMapping struct {
	Voltage    Modbus `json:"voltage"`
	Load       Modbus `json:"load"`
	Generation Modbus `json:"generation"`
	PhaseAngle Modbus `json:"phaseAngle"`
}

type Modbus struct {
	Type          string  `json:"type"`
	Address       uint16  `json:"address"`
	DataType      string  `json:"dataType"`
	ScalingFactor float64 `json:"scalingFactor"`
}

type DeviceMetadata struct {
	Description string `json:"description"`
}
