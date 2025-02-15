#!/bin/bash

cd ../config
kubectl apply -f role.yaml
kubectl apply -f role-binding.yaml

cd ../test
kubectl apply -f test-pod.yaml
kubectl apply -f keycloak-persistent-volume.yaml
kubectl apply -f keycloak.yaml

cd ../gateway
kubectl apply -f gateway-deployment.yaml
kubectl apply -f gateway-service.yaml

cd ../tenant
kubectl apply -f tenant-deployment.yaml
kubectl apply -f tenant-service.yaml
