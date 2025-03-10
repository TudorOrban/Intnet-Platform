package com.intnet.admin.features.intnetservice.service;

import com.intnet.admin.features.intnetservice.model.ServiceKubernetesData;
import com.intnet.admin.features.intnetservice.model.ServiceStatus;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.models.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IntnetServiceKubernetesServiceImpl implements IntnetServiceKubernetesService {

    private final AppsV1Api appsV1Api;

    public IntnetServiceKubernetesServiceImpl(AppsV1Api appsV1Api) {
        this.appsV1Api = appsV1Api;
    }

    public Map<String, ServiceKubernetesData> getServices(List<String> serviceNames, String namespace) {
        namespace = namespace == null ? "default" : namespace;

        Map<String, ServiceKubernetesData> serviceDataMap = new HashMap<>();

        for (String serviceName : serviceNames) {
            try {
                V1Deployment deployment = appsV1Api.readNamespacedDeployment(serviceName, namespace).execute();
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

    public void rolloutRestartDeployments(List<String> serviceNames, String namespace) {
        namespace = namespace == null ? "default" : namespace;

        for (String serviceName : serviceNames) {
            try {
                V1Deployment deployment = appsV1Api.readNamespacedDeployment(serviceName, namespace).execute();
                if (deployment.getMetadata() == null) {
                    System.out.println("Failed to get service metadata for: " + serviceName);
                    continue;
                }
                // Create a new annotation for the restart
                Map<String, String> annotations = deployment.getMetadata().getAnnotations();
                if (annotations == null) {
                    annotations = new HashMap<>();
                }
                annotations.put("kubectl.kubernetes.io/restartedAt", Instant.now().toString());

                deployment.getMetadata().setAnnotations(annotations);
                appsV1Api.replaceNamespacedDeployment(serviceName, namespace, deployment);

                System.out.println("Rollout restart initiated for deployment: " + serviceName + " in namespace: " + namespace);
            } catch (ApiException e) {
                System.err.println("Error restarting deployment: " + serviceName + ". Error: " + e);
            }
        }
    }

    private ServiceKubernetesData getKubernetesData(V1DeploymentStatus status) {
        int replicas = status.getReplicas() != null ? status.getReplicas() : 0;
        Integer availableReplicas = status.getAvailableReplicas() != null ? status.getAvailableReplicas() : 0;
        Integer unavailableReplicas = status.getUnavailableReplicas() != null ? status.getUnavailableReplicas() : 0;

        ServiceStatus deploymentStatus = ServiceStatus.UNKNOWN;
        if (availableReplicas.equals(replicas)) {
            deploymentStatus = ServiceStatus.RUNNING;
        } else if (unavailableReplicas.equals(replicas)) {
            deploymentStatus = ServiceStatus.STOPPED;
        } else if (replicas > 0 && availableReplicas == 0) {
            deploymentStatus = ServiceStatus.PENDING;
        }

        return new ServiceKubernetesData(deploymentStatus, replicas, availableReplicas, "default");
    }
}
