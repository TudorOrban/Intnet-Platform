#!/bin/bash

cd ../config || exit
kubectl apply -f role.yaml
kubectl apply -f role-binding.yaml

cd ../keycloak || exit
kubectl apply -f test-pod.yaml
kubectl apply -f keycloak-persistent-volume.yaml
kubectl apply -f keycloak-deployment.yaml
kubectl apply -f keycloak-service.yaml

cd ../gateway || exit
kubectl apply -f gateway-deployment.yaml
kubectl apply -f gateway-service.yaml

cd ../tenant || exit
kubectl apply -f tenant-deployment.yaml
kubectl apply -f tenant-service.yaml

cd ../neo4j || exit
kubectl apply -f neo4j-persistent-volume.yaml
kubectl apply -f neo4j-deployment.yaml
kubectl apply -f neo4j-service.yaml

cd ../grid-topology || exit
kubectl apply -f grid-topology-deployment.yaml
kubectl apply -f grid-topology-service.yaml

cd ../grid-data || exit
kubectl apply -f grid-data-deployment.yaml
kubectl apply -f grid-data-service.yaml

cd ../timescale-db || exit
kubectl apply -f timescale-db-persistent-volume-claim.yaml
kubectl apply -f timescale-db-deployment.yaml
kubectl apply -f timescale-db-service.yaml

cd ../grid-history || exit
kubectl apply -f grid-history-deployment.yaml
kubectl apply -f grid-history-service.yaml

cd ../grafana || exit
kubectl apply -f grafana-persistent-volume-claim.yaml
kubectl apply -f grafana-deployment.yaml
kubectl apply -f grafana-service.yaml