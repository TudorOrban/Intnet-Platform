package main

import (
	"log"
	"modbusadapter/api/core"
	"modbusadapter/internal/di"
)

func main() {
	container := di.BuildContainer()

	err := container.Invoke(func(server *core.Server) {
		server.Start()
	})
	if err != nil {
		log.Printf("Failed to invoke DI container: %v", err)
	}
}
