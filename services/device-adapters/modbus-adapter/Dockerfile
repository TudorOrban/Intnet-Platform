FROM golang:1.24 AS builder

WORKDIR /app

COPY go.mod go.sum ./

RUN go mod download

COPY . .

RUN go build -o modbus-adapter ./main.go

FROM alpine:latest

WORKDIR /app

COPY --from=builder /app/modbus-adapter /app/modbus-adapter
COPY --from=builder /app/api /app/api
COPY --from=builder /app/internal /app/internal
COPY --from=builder /app/vendor /app/vendor

EXPOSE 8080

CMD ["./modbus-adapter"]