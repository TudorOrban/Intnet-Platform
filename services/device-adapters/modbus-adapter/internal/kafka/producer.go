package kafka

import (
	"encoding/json"
	"fmt"
	"log"

	"github.com/confluentinc/confluent-kafka-go/v2/kafka"
)

type StdKafkaProducer struct {
	producer *kafka.Producer
	topic    string
}

func NewKafkaProducer() Producer {
	return &StdKafkaProducer{}
}

func NewStdKafkaProducer(brokers string, topic string) (Producer, error) {
	config := &kafka.ConfigMap{
		"bootstrap.servers": brokers,
	}

	producer, err := kafka.NewProducer(config)
	if err != nil {
		return nil, fmt.Errorf("failed to create Kafka producer: %w", err)
	}

	return &StdKafkaProducer{producer: producer, topic: topic}, nil
}

func (p *StdKafkaProducer) Produce(message ModbusMessage) error {
	messageBytes, err := json.Marshal(message)
	if err != nil {
		return fmt.Errorf("failed to marshal message: %w", err)
	}

	deliveryChan := make(chan kafka.Event)
	defer close(deliveryChan)

	err = p.producer.Produce(&kafka.Message{
		TopicPartition: kafka.TopicPartition{Topic: &p.topic, Partition: kafka.PartitionAny},
		Value:          messageBytes,
	}, deliveryChan)
	if err != nil {
		return fmt.Errorf("failed to produce message: %w", err)
	}

	e := <-deliveryChan
	m := e.(*kafka.Message)

	// if m.Error != nil {
	// 	return fmt.Errorf("message delivery failed: %w", m.Error)
	// }

	log.Printf("Message delivered to topic %s at offset %d\n", *m.TopicPartition.Topic, m.TopicPartition.Offset)

	return nil
}

func (p *StdKafkaProducer) Close() {
	p.producer.Close()
}
