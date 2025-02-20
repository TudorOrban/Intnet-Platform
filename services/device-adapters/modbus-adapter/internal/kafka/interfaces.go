package kafka

type Producer interface {
	Produce(message ModbusMessage) error
	Close()
}