package kafka

import (
	"encoding/json"
	"fmt"
	"log"

	"github.com/confluentinc/confluent-kafka-go/v2/kafka"
)

type KafkaConfig struct {
	Brokers string
	Topic   string
}

type StdKafkaProducer struct {
	producer *kafka.Producer
	topic    string
}

func NewKafkaProducer() Producer {
	return &StdKafkaProducer{}
}

func NewStdKafkaProducer(config KafkaConfig) (Producer, error) {
	configMap := &kafka.ConfigMap{
		"bootstrap.servers": config.Brokers,
	}
	log.Printf("Topic: %s", config.Topic)
	producer, err := kafka.NewProducer(configMap)
	if err != nil {
		return nil, fmt.Errorf("failed to create Kafka producer: %w", err)
	}

	return &StdKafkaProducer{producer: producer, topic: config.Topic}, nil
}

func (p *StdKafkaProducer) Produce(message ModbusMessage) error {
	messageBytes, err := json.Marshal(message)
	if err != nil {
		return fmt.Errorf("failed to marshal message: %w", err)
	}

	log.Printf("Sending JSON: %s\n", string(messageBytes))

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

	log.Printf("Message delivered to topic %s at offset %d\n", *m.TopicPartition.Topic, m.TopicPartition.Offset)

	return nil
}

func (p *StdKafkaProducer) Close() {
	p.producer.Close()
}
