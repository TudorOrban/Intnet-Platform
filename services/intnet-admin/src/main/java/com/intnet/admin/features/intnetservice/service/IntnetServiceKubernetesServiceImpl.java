package com.intnet.admin.features.intnetservice.service;

import com.intnet.admin.features.intnetservice.model.PodData;
import com.intnet.admin.features.intnetservice.model.ServiceKubernetesData;
import com.intnet.admin.features.intnetservice.model.ServiceStatus;
import com.intnet.admin.shared.exception.types.KubernetesException;
import io.kubernetes.client.PodLogs;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.*;
import io.kubernetes.client.util.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.*;

@Service
public class IntnetServiceKubernetesServiceImpl implements IntnetServiceKubernetesService {

    private final AppsV1Api appsV1Api;
    private final CoreV1Api coreV1Api;
    private final DefaultDataBufferFactory dataBufferFactory;
    private final Logger logger = LoggerFactory.getLogger(IntnetServiceKubernetesServiceImpl.class);

    public IntnetServiceKubernetesServiceImpl(
            AppsV1Api appsV1Api,
            CoreV1Api coreV1Api
    ) {
        this.appsV1Api = appsV1Api;
        this.coreV1Api = coreV1Api;
        this.dataBufferFactory = new DefaultDataBufferFactory();
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
                ServiceKubernetesData data = this.mapV1DeploymentStatusToServiceKubernetesData(status);
                serviceDataMap.put(serviceName, data);
            } catch (ApiException e) {
                logger.error("Error fetching Kubernetes data for {}: {}", serviceName, e.getResponseBody());
                serviceDataMap.put(serviceName, null);
            }
        }

        return serviceDataMap;
    }

    public ServiceKubernetesData getServiceWithPods(String serviceName, String namespace) {
        namespace = namespace == null ? "default" : namespace;

        try {
            V1Deployment deployment = appsV1Api.readNamespacedDeployment(serviceName, namespace).execute();
            if (deployment.getSpec() == null || deployment.getSpec().getSelector().getMatchLabels() == null) {
                throw new KubernetesException("Missing Deployment Spec");
            }
            V1DeploymentStatus status = deployment.getStatus();
            if (status == null) {
                throw new KubernetesException("Failed to get Deployment status");
            }
            ServiceKubernetesData data = this.mapV1DeploymentStatusToServiceKubernetesData(status);

            String selector = deployment.getSpec().getSelector().getMatchLabels().entrySet().stream()
                    .map(entry -> entry.getKey() + "=" + entry.getValue())
                    .reduce((a, b) -> a + "," + b)
                    .orElse("");

            V1PodList podList = coreV1Api.listNamespacedPod(namespace)
                    .labelSelector(selector)
                    .execute();
            List<PodData> pods = podList.getItems().stream().map(this::mapV1PodToPodData).toList();
            data.setPods(pods);

            return data;
        } catch (ApiException e) {
            logger.error("Error fetching pods for {}: {}", serviceName, e.getResponseBody());
            throw new KubernetesException(e.getMessage());
        }
    }

    private ServiceKubernetesData mapV1DeploymentStatusToServiceKubernetesData(V1DeploymentStatus status) {
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

        return new ServiceKubernetesData(deploymentStatus, replicas, availableReplicas, "default", new ArrayList<>());
    }

    private PodData mapV1PodToPodData(V1Pod pod) {
        PodData podData = new PodData();
        if (pod.getMetadata() != null) {
            podData.setName(pod.getMetadata().getName());
            podData.setNamespace(pod.getMetadata().getNamespace());
        }
        if (pod.getStatus() != null) {
            podData.setStatus(pod.getStatus().getPhase());
            if (pod.getStatus().getStartTime() != null) {
                podData.setStartTime(pod.getStatus().getStartTime().toString());
            }
        }
        if (pod.getSpec() != null) {
            podData.setNodeName(pod.getSpec().getNodeName());
        }
        return podData;
    }

    public Flux<String> streamPodLogs(String podName, String namespace, String containerName) {
        String finalNamespace = namespace == null ? "default" : namespace;
        String finalContainerName = this.getPodContainerName(podName, namespace);

        return Flux.create(sink -> {
            try {
                PodLogs logs = new PodLogs(Config.defaultClient());
                InputStream inputStream = logs.streamNamespacedPodLog(finalNamespace, podName, finalContainerName);
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
//                    System.out.println(line);
                    byte[] utf8Bytes = line.getBytes(StandardCharsets.UTF_8);
                    String base64Encoded = Base64.getEncoder().encodeToString(utf8Bytes);
                    String eventData = "data: " + base64Encoded + "\n\n";
                    sink.next(eventData);
                    System.out.println("Data sent to sink.next(): " + eventData);
                }

                reader.close();
                sink.complete();
                System.out.println("Flux completed");
            } catch (IOException | ApiException e) {
                logger.error("Error streaming pod logs: {}", e.getMessage(), e);
                sink.error(e);
            }
        });
    }

    private String getPodContainerName(String podName, String namespace) {
        String defaultContainerName = "";
        try {
            V1Pod pod = coreV1Api.readNamespacedPod(podName, namespace).execute();
            if (pod.getSpec() != null) {
                List<V1Container> containers = pod.getSpec().getContainers();

                if (containers.isEmpty()) {
                    throw new KubernetesException("No containers found for pod " + podName);
                }

                defaultContainerName = containers.getFirst().getName();
            }
        } catch (ApiException e) {
            throw new KubernetesException("Error fetching pod container: " + e.getMessage());
        }
        return defaultContainerName;
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
                logger.error("Error restarting deployment: {}. Error: {}", serviceName, e.getMessage());
            }
        }
    }

}
