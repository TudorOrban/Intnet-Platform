#!/bin/bash

cd ../config || exit
kubectl apply -f role.yaml
kubectl apply -f role-binding.yaml

cd ../test || exit
kubectl apply -f test-pod.yaml
kubectl apply -f keycloak-persistent-volume.yaml
kubectl apply -f keycloak.yaml

cd ../gateway || exit
kubectl apply -f gateway-deployment.yaml
kubectl apply -f gateway-service.yaml

cd ../tenant || exit
kubectl apply -f tenant-deployment.yaml
kubectl apply -f tenant-service.yaml

cd ../network || exit
kubectl apply -f network-deployment.yaml
kubectl apply -f network-service.yaml

