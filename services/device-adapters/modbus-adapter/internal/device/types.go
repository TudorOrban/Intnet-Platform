package device

type Device struct {
	ID               int64            `json:"id"`
	IPAddress        string           `json:"ipAddress"`
	Protocol         string           `json:"protocol"`
	DeviceDataConfig DeviceDataConfig `json:"deviceDataConfig"`
	Latitude         float32          `json:"latitude"`
	Longitude        float32          `json:"longitude"`
	Metadata         DeviceMetadata   `json:"metadata"`
}

type DeviceDataConfig struct {
	Voltage    DeviceVariableConfig `json:"voltage"`
	Load       DeviceVariableConfig `json:"load"`
	Generation DeviceVariableConfig `json:"generation"`
	PhaseAngle DeviceVariableConfig `json:"phaseAngle"`
}

type DeviceVariableConfig struct {
	Type                 string  `json:"type"`
	Address              uint16  `json:"address"`
	DataType             string  `json:"dataType"`
	ScalingFactor        float64 `json:"scalingFactor"`
	PollFrequencySeconds float64 `json:"pollFrequencySeconds"`
}

type DeviceMetadata struct {
	Description string `json:"description"`
}
