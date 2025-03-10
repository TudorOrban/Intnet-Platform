package com.intnet.admin.features.intnetservice.service;

import com.intnet.admin.features.intnetservice.model.PodData;
import com.intnet.admin.features.intnetservice.model.ServiceKubernetesData;
import com.intnet.admin.features.intnetservice.model.ServiceStatus;
import com.intnet.admin.shared.exception.types.KubernetesException;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IntnetServiceKubernetesServiceImpl implements IntnetServiceKubernetesService {

    private final AppsV1Api appsV1Api;
    private final CoreV1Api coreV1Api;
    private final Logger logger = LoggerFactory.getLogger(IntnetServiceKubernetesServiceImpl.class);

    public IntnetServiceKubernetesServiceImpl(
            AppsV1Api appsV1Api,
            CoreV1Api coreV1Api
    ) {
        this.appsV1Api = appsV1Api;
        this.coreV1Api = coreV1Api;
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
                logger.error("Error fetching Kubernetes data for {}: {}", serviceName, e.getResponseBody());
                serviceDataMap.put(serviceName, null);
            }
        }

        return serviceDataMap;
    }

    public List<PodData> getPodsForService(String serviceName, String namespace) {
        namespace = namespace == null ? "default" : namespace;

        try {
            V1Deployment deployment = appsV1Api.readNamespacedDeployment(serviceName, namespace).execute();
            if (deployment.getSpec() == null || deployment.getSpec().getSelector().getMatchLabels() == null) {
                throw new KubernetesException("Missing Deployment Spec");
            }
            String selector = deployment.getSpec().getSelector().getMatchLabels().entrySet().stream()
                    .map(entry -> entry.getKey() + "=" + entry.getValue())
                    .reduce((a, b) -> a + "," + b)
                    .orElse("");

            V1PodList podList = coreV1Api.listNamespacedPod(namespace)
                    .labelSelector(selector)
                    .execute();
            return podList.getItems().stream().map(this::mapV1PodToPodData).toList();
        } catch (ApiException e) {
            logger.error("Error fetching pods for {}: {}", serviceName, e.getResponseBody());
            throw new KubernetesException(e.getMessage());
        }
    }

    private PodData mapV1PodToPodData(V1Pod pod) {
        PodData podData = new PodData();
        if (pod.getMetadata() != null) {
            podData.setName(pod.getMetadata().getName());
            podData.setNamespace(pod.getMetadata().getNamespace());
        }
        if (pod.getStatus() != null) {
            podData.setStatus(pod.getStatus().getPhase());
            podData.setStartTime(pod.getStatus().getStartTime().toString());
        }
        if (pod.getSpec() != null) {
            podData.setNodeName(pod.getSpec().getNodeName());
        }
        return podData;
    }

    public void rolloutRestartDeployments(List<String> serviceNames, String namespace) {
        namespace = namespace == null ? "default" : namespace;

        for (String serviceName : serviceNames) {
            try {
                V1Deployment deployment = appsV1Api.readNamespacedDeployment(serviceName, namespace).execute();

                if (deployment.getMetadata() == null || deployment.getSpec() == null) {
                    logger.warn("Failed to get service metadata or template for: {}", serviceName);
                    continue;
                } else {
                    deployment.getSpec().getTemplate();
                }

                V1PodTemplateSpec template = deployment.getSpec().getTemplate();
                if (template.getMetadata() == null) {
                    logger.warn("Failed to get template metadata: {}", serviceName);
                    continue;
                }
                Map<String, String> annotations = template.getMetadata().getAnnotations();

                if (annotations == null) {
                    annotations = new HashMap<>();
                }

                annotations.put("kubectl.kubernetes.io/restartedAt", Instant.now().toString());

                template.getMetadata().setAnnotations(annotations);
                appsV1Api.replaceNamespacedDeployment(serviceName, namespace, deployment).execute();

                logger.info("Rollout restart initiated for deployment: {} in namespace: {}", serviceName, namespace);
            } catch (ApiException e) {
                logger.error("Error restarting deployment: {}. Error: {}", serviceName, e);
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
