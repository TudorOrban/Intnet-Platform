package main

import (
	"log"
	"modbusadapter/api/core"
)

func main() {
	container := buildContainer()

	err := container.Invoke(func(server *core.Server) {
		server.Start()
	})
	if err != nil {
		log.Printf("Failed to invoke DI container: %v", err)
	}
}