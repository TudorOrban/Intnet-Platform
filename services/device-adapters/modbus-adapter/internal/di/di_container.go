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
	container.Provide(func() string {
		kafkaBrokers := os.Getenv("KAFKA_BROKERS")
		if kafkaBrokers == "" {
			kafkaBrokers = "localhost:9092"
			log.Println("Using default Kafka brokers: ", kafkaBrokers)
		}
		return kafkaBrokers
	})

	container.Provide(func() string {
		kafkaTopic := os.Getenv("KAFKA_TOPIC")
		if kafkaTopic == "" {
			kafkaTopic = "modbus_data"
			log.Println("Using default Kafka topic: ", kafkaTopic)
		}
		return kafkaTopic
	})

	container.Provide(kafka.NewStdKafkaProducer, dig.As(new(kafka.Producer)))
}
