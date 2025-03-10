package com.intnet.admin.features.intnetservice.service;

import com.intnet.admin.features.intnetservice.model.PodData;
import com.intnet.admin.features.intnetservice.model.ServiceKubernetesData;

import java.util.List;
import java.util.Map;

public interface IntnetServiceKubernetesService {

    Map<String, ServiceKubernetesData> getServices(List<String> serviceNames, String namespace);
    List<PodData> getPodsForService(String serviceName, String namespace);
    void rolloutRestartDeployments(List<String> serviceNames, String namespace);
}
