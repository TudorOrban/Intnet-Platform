#!/bin/bash

set -e

echo "Initializing Intnet local environmet..."

# --- Dependency Checks and Installation ---

echo "Checking Docker installation..."
if ! command -v docker &> /dev/null; then
    echo "Docker is not installed. Installing Docker..."
    sudo apt-get update
    sudo apt-get install -y apt-transport-https ca-certificates curl gnupg lsb-release
    curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg
    echo "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
    sudo apt-get update
    sudo apt-get install -y docker-ce docker-ce-cli containerd.io
    sudo systemctl enable docker
    sudo systemctl start docker
    echo "Docker installed successfully."
else
    echo "Docker is already installed."
fi

echo "Checking Minikube installation..."
if ! command -v minikube &> /dev/null; then
    echo "Minikube is not installed. Installing Minikube..."
    curl -Lo minikube https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64
    chmod +x minikube
    sudo install minikube /usr/local/bin
    echo "Minikube installed successfully."
else
    echo "Minikube is already installed."
fi

echo "Checking kubectl installation..."
if ! command -v kubectl &> /dev/null; then
    echo "kubectl is not installed. Installing kubectl..."
    curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
    sudo install -o root -g root -m 0755 kubectl /usr/local/bin/kubectl
    echo "kubectl installed successfully."
else
    echo "kubectl is already installed."
fi

echo "Checking Java 21 installation..."
if ! java -version 2>&1 | grep -q "21\."; then
    echo "Java 21 is not installed. Installing Java 21..."
    sudo apt-get install -y openjdk-21-jdk
    echo "Java 21 installed successfully."
else
    echo "Java 21 is already installed."
fi

echo "Checking Maven installation..."
if ! command -v mvn &> /dev/null; then
    echo "Maven is not installed. Installing Maven..."
    sudo apt-get install -y maven
    echo "Maven installed successfully."
else
    echo "Maven is already installed."
fi

echo "Dependencies checked and installed."

# --- Minikube Setup

echo "Starting Minikube..."
minikube start --driver=docker
echo "Minikube started successfully."

