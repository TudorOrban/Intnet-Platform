package com.intnet.admin.features.intnetservice.service;

import com.intnet.admin.features.intnetservice.model.IntnetService;
import com.intnet.admin.features.intnetservice.model.ServiceKubernetesData;
import com.intnet.admin.features.intnetservice.repository.IntnetServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class IntnetServiceManagerServiceImpl implements IntnetServiceManagerService {

    private final IntnetServiceRepository serviceRepository;
    private final IntnetServiceKubernetesService kubernetesService;

    @Autowired
    public IntnetServiceManagerServiceImpl(
            IntnetServiceRepository serviceRepository,
            IntnetServiceKubernetesService kubernetesService
    ) {
        this.serviceRepository = serviceRepository;
        this.kubernetesService = kubernetesService;
    }

    public List<IntnetService> getServices() {
        List<IntnetService> services = serviceRepository.findAll();
        List<String> serviceNames = services.stream().map(IntnetService::getName).toList();
        Map<String, ServiceKubernetesData> serviceDataMap = kubernetesService.getServices(serviceNames);

        for (IntnetService service: services) {
            ServiceKubernetesData kubernetesData = serviceDataMap.get(service.getName());
            if (kubernetesData == null) {
                continue;
            }
            service.setKubernetesData(kubernetesData);
        }

        return services;
    }
}
