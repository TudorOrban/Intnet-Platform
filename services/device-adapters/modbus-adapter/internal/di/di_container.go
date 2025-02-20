package di

import (
	"log"
	"modbusadapter/api/core"
	"modbusadapter/internal/device"
	"modbusadapter/internal/kafka"
	"os"

	"go.uber.org/dig"
)

func BuildContainer() *dig.Container {
	container := dig.New()

	provideKafkaProducer(container)

	container.Provide(device.NewStdDeviceReader)
	container.Provide(device.NewStdDeviceManager)
	container.Provide(core.NewConnectionHandler)
	container.Provide(core.NewServer)

	return container
}

func provideKafkaProducer(container *dig.Container) {
    container.Provide(provideKafkaConfig)

    container.Provide(
        func(config kafka.KafkaConfig) (kafka.Producer, error) {
            return kafka.NewStdKafkaProducer(config)
        },
        dig.As(new(kafka.Producer)),
    )
}

func provideKafkaConfig() kafka.KafkaConfig {
    brokers := os.Getenv("KAFKA_BROKERS")
    if brokers == "" {
        brokers = "localhost:9092"
        log.Println("Using default Kafka brokers: ", brokers)
    }

    topic := os.Getenv("KAFKA_TOPIC")
    if topic == "" {
        topic = "modbus_data"
        log.Println("Using default Kafka topic: ", topic)
    }

    return kafka.KafkaConfig{
        Brokers: brokers,
        Topic:   topic,
    }
}