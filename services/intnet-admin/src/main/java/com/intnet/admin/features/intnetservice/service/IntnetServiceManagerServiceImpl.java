package com.intnet.admin.features.intnetservice.service;

import com.intnet.admin.features.intnetservice.model.IntnetService;
import com.intnet.admin.features.intnetservice.model.ServiceKubernetesData;
import com.intnet.admin.features.intnetservice.repository.IntnetServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

@Service
public class IntnetServiceManagerServiceImpl implements IntnetServiceManagerService {

    private final IntnetServiceRepository serviceRepository;
    private final IntnetServiceKubernetesService kubernetesService;
    private final ImageBuilderService imageBuilderService;

    @Autowired
    public IntnetServiceManagerServiceImpl(
            IntnetServiceRepository serviceRepository,
            IntnetServiceKubernetesService kubernetesService,
            ImageBuilderService imageBuilderService
    ) {
        this.serviceRepository = serviceRepository;
        this.kubernetesService = kubernetesService;
        this.imageBuilderService = imageBuilderService;
    }

    public List<IntnetService> getServices(String namespace) {
        List<IntnetService> services = serviceRepository.findAll();
        List<String> serviceNames = services.stream().map(IntnetService::getName).toList();
        Map<String, ServiceKubernetesData> serviceDataMap = kubernetesService.getServices(serviceNames, namespace);

        for (IntnetService service: services) {
            ServiceKubernetesData kubernetesData = serviceDataMap.get(service.getName());
            if (kubernetesData == null) {
                continue;
            }
            service.setKubernetesData(kubernetesData);
        }

        return services;
    }

    public ServiceKubernetesData getServiceWithPods(String serviceName, String namespace) {
        return kubernetesService.getServiceWithPods(serviceName, namespace);
    }

    public Flux<String> streamPodLogs(String podName, String namespace, String containerName) {
        return kubernetesService.streamPodLogs(podName, namespace, containerName);
    }

    public void rolloutRestartServiceDeployments(List<String> serviceNames, String namespace) {
        kubernetesService.rolloutRestartDeployments(serviceNames, namespace);
    }

    public Flux<DataBuffer> buildServiceImages(List<String> serviceNames) {
        List<IntnetService> services = serviceRepository.findByNames(serviceNames);
        return imageBuilderService.buildImages(services);
    }
}
