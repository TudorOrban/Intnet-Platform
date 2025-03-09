#!/bin/bash

service_name=$1
docker_filepath=$2
docker_build_type=$3

cd "../${docker_filepath}"

if docker_build_type = JAVA; then
    mvn clean package -DskipTests

docker build -t "${service_name}:latest" .