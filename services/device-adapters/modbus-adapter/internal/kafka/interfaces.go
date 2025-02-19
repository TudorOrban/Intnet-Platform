package kafka

type Producer interface {
	Produce(message []byte, topic string) error
}