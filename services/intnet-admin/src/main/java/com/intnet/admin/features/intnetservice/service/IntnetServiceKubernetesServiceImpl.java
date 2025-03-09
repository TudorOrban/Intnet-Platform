package com.intnet.admin.features.intnetservice.service;

import com.intnet.admin.features.intnetservice.model.ServiceKubernetesData;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.models.V1Deployment;
import io.kubernetes.client.openapi.models.V1DeploymentStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class IntnetServiceKubernetesServiceImpl implements IntnetServiceKubernetesService {

    private final AppsV1Api appsV1Api;

    public IntnetServiceKubernetesServiceImpl(AppsV1Api appsV1Api) {
        this.appsV1Api = appsV1Api;
    }

    public Map<String, ServiceKubernetesData> getServices(List<String> serviceNames) {
        Map<String, ServiceKubernetesData> serviceDataMap = new HashMap<>();

        for (String serviceName : serviceNames) {
            try {
                V1Deployment deployment = appsV1Api.readNamespacedDeployment(serviceName, "default").execute();
                V1DeploymentStatus status = deployment.getStatus();
                if (status == null) {
                    continue;
                }
                ServiceKubernetesData data = this.getKubernetesData(status);
                serviceDataMap.put(serviceName, data);
            } catch (ApiException e) {
                System.err.println("Error fetching Kubernetes data for " + serviceName + ": " + e.getResponseBody());
                serviceDataMap.put(serviceName, null);
            }
        }

        return serviceDataMap;
    }

    private ServiceKubernetesData getKubernetesData(V1DeploymentStatus status) {
        Integer replicas = status.getReplicas() != null ? status.getReplicas() : 0;

        String deploymentStatus = "Unknown";
        if (status.getAvailableReplicas() != null && Objects.equals(status.getAvailableReplicas(), replicas)) {
            deploymentStatus = "Running";
        } else if (status.getUnavailableReplicas() != null && Objects.equals(status.getUnavailableReplicas(), replicas)) {
            deploymentStatus = "Stopped";
        }

        return new ServiceKubernetesData(deploymentStatus, replicas, "default");
    }
}
