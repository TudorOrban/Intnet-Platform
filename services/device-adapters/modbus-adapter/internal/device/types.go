package device

type Device struct {
	ID            string         `json:"id"`
	IPAddress     string         `json:"ipAddress"`
	Protocol      string         `json:"protocol"`
	DataStructure string         `json:"dataStructure"`
	Latitude      float32        `json:"latitude"`
	Longitude     float32        `json:"longitude"`
	Metadata      DeviceMetadata `json:"metadata"`
}

type DeviceMetadata struct {
	Description string `json:"description"`
}
