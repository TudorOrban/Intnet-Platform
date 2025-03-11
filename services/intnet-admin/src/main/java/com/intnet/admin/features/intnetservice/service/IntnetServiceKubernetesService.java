package com.intnet.admin.features.intnetservice.service;

import com.intnet.admin.features.intnetservice.model.ServiceKubernetesData;
import org.springframework.core.io.buffer.DataBuffer;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

public interface IntnetServiceKubernetesService {

    Map<String, ServiceKubernetesData> getServices(List<String> serviceNames, String namespace);
    ServiceKubernetesData getServiceWithPods(String serviceName, String namespace);
    Flux<String> streamPodLogs(String podName, String namespace, String containerName);

    void rolloutRestartDeployments(List<String> serviceNames, String namespace);
}
