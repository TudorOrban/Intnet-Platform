package kafka

type KafkaProducer struct {

}

func NewKafkaProducer() Producer {
	return &KafkaProducer{}
}

func (p *KafkaProducer) Produce(message []byte, topic string) error {
	return nil
}