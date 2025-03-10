#!/bin/bash

service_name="$1"
docker_filepath="$2"
docker_build_type="$3"

# Change directory and handle errors
if cd "../../../${docker_filepath}"; then
  echo "Changed directory to: $(pwd)"
else
  echo "Error: Could not change directory to ../${docker_filepath}"
  exit 1
fi

# Build Java service if specified and handle errors
if [ "$docker_build_type" == "JAVA" ]; then
  if mvn clean package -DskipTests; then
    echo "Maven build successful"
  else
    echo "Error: Maven build failed"
    exit 1
  fi
fi

# Set minikube docker environment and handle errors
if eval $(minikube docker-env); then
  echo "Minikube docker environment set"
else
  echo "Error: Could not set minikube docker environment"
  exit 1
fi

# Build Docker image and handle errors
if docker build -t "${service_name}:latest" .; then
  echo "Docker build successful"
else
  echo "Error: Docker build failed"
  exit 1
fi

echo "Build process completed"