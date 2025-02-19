package device

type DeviceManager interface {
	GetDevices() ([]Device, error)
}