package core

import (
	"log"
	"net/http"
	"time"

	"github.com/gorilla/mux"
)

type Server struct {
	router *mux.Router
	ConnectionHandler *ConnectionHandler
}

func NewServer(
	connectionHandler *ConnectionHandler,
) *Server {
	s := &Server{
		router: mux.NewRouter(),
		ConnectionHandler: connectionHandler,
	}
	s.routes()
	return s
}

func (s *Server) routes() {
	s.router.HandleFunc("/start-connection", s.ConnectionHandler.handleStartConnectionHandler).Methods("POST")	
}

func (s *Server) Start() {
	server := &http.Server{
		Addr: ":8080",
		Handler: s.router,
		ReadTimeout: 5 * time.Minute,
		WriteTimeout: 5 * time.Minute,
		IdleTimeout: 1 * time.Minute,
	}

	if err := server.ListenAndServe(); err != http.ErrServerClosed {
		log.Printf("Failed to start server: %v", err)
		return
	}
	log.Printf("Server is running on http://localhost:8080")
}