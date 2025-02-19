package device

type StdDeviceManager struct {
	url string
}

func NewStdDeviceManager() DeviceManager {
	return &StdDeviceManager{url: "test"}
}

func (d *StdDeviceManager) GetDevices() ([]Device, error) {
	devices := []Device{}
	return devices, nil
}
