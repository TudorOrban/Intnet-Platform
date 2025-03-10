package com.intnet.admin.features.intnetservice.service;

import com.intnet.admin.features.intnetservice.model.PodData;
import com.intnet.admin.features.intnetservice.model.ServiceKubernetesData;
import com.intnet.admin.features.intnetservice.model.ServiceStatus;
import com.intnet.admin.shared.exception.types.KubernetesException;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IntnetServiceKubernetesServiceImpl implements IntnetServiceKubernetesService {

    private final AppsV1Api appsV1Api;
    private final CoreV1Api coreV1Api;
    private final OkHttpClient okHttpClient;
    private final Logger logger = LoggerFactory.getLogger(IntnetServiceKubernetesServiceImpl.class);

    public IntnetServiceKubernetesServiceImpl(
            AppsV1Api appsV1Api,
            CoreV1Api coreV1Api,
            OkHttpClient okHttpClient
    ) {
        this.appsV1Api = appsV1Api;
        this.coreV1Api = coreV1Api;
        this.okHttpClient = okHttpClient;
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

    public Flux<DataBuffer> streamPodLogs(String podName, String namespace, String containerName) {
        String finalNamespace = namespace == null ? "default" : namespace;
        String logUrl = buildLogUrl(podName, finalNamespace, containerName);

        return Mono.fromCallable(() -> fetchLogs(logUrl))
                .flatMapMany(this::readLogsToFlux);
    }

    private String buildLogUrl(String podName, String namespace, String containerName) {
        String baseUrl = coreV1Api.getApiClient().getBasePath() +
                "/api/v1/namespaces/" + namespace +
                "/pods/" + podName +
                "/log?follow=true&pretty=false&timestamps=true";

        if (containerName != null && !containerName.isEmpty()) {
            baseUrl += "&container=" + containerName;
        }

        return baseUrl;
    }

    private BufferedReader fetchLogs(String logUrl) throws IOException, ApiException {
        Request request = new Request.Builder().url(logUrl).build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new ApiException(response.code(), response.message());
            }

            ResponseBody responseBody = response.body();
            if (responseBody == null) {
                throw new IOException("Empty response body");
            }

            return new BufferedReader(new InputStreamReader(responseBody.byteStream()));
        }
    }

    private Flux<DataBuffer> readLogsToFlux(BufferedReader reader) {
        return Flux.generate(
                () -> reader,
                (bufferedReader, sink) -> {
                    try {
                        String line = bufferedReader.readLine();
                        if (line != null) {
                            sink.next(DefaultDataBufferFactory.sharedInstance.wrap(line.getBytes()));
                        } else {
                            sink.complete();
                        }
                    } catch (IOException e) {
                        sink.error(e);
                    }
                    return bufferedReader;
                },
                bufferedReader -> {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        logger.error("Error closing BufferedReader", e);
                    }
                }
        );
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
