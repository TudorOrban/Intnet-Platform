# Build stage
FROM golang:1.24 AS builder

WORKDIR /app

# Install librdkafka development files
RUN apt-get update && apt-get install -y librdkafka-dev

COPY go.mod go.sum ./

RUN go mod download

COPY . .

RUN go build -o modbus-adapter -ldflags "-extldflags=-static" ./main.go

# Final stage (consistent Debian-based image)
FROM debian:bullseye-slim

WORKDIR /app

# Copy necessary files
COPY --from=builder /app/modbus-adapter /app/modbus-adapter
COPY --from=builder /app/api /app/api
COPY --from=builder /app/internal /app/internal
COPY --from=builder /app/vendor /app/vendor

# Copy librdkafka libraries (IMPORTANT for static linking with cgo)
COPY --from=builder /usr/lib/x86_64-linux-gnu/librdkafka* /usr/lib/x86_64-linux-gnu/

# Set executable permissions
RUN chmod +x /app/modbus-adapter

EXPOSE 8080

CMD ["./modbus-adapter"]