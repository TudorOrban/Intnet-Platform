package main

import (
	"modbusadapter/api/core"
	"modbusadapter/internal/device"
	"modbusadapter/internal/kafka"

	"go.uber.org/dig"
)

func buildContainer() *dig.Container {
	container := dig.New()

	container.Provide(device.NewStdDeviceManager)
	container.Provide(kafka.NewKafkaProducer)
	container.Provide(core.NewConnectionHandler)
	container.Provide(core.NewServer)

	return container
}
