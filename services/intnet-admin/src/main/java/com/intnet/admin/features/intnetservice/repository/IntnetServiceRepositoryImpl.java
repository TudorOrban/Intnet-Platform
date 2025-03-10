package com.intnet.admin.features.intnetservice.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intnet.admin.features.intnetservice.model.IntnetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class IntnetServiceRepositoryImpl implements IntnetServiceRepository {

    private final ObjectMapper objectMapper;
    private final ResourceLoader resourceLoader;

    @Value("${intnet.services.config}")
    private String servicesConfigPath;

    @Autowired
    public IntnetServiceRepositoryImpl(ObjectMapper objectMapper, ResourceLoader resourceLoader) {
        this.objectMapper = objectMapper;
        this.resourceLoader = resourceLoader;
    }

    public List<IntnetService> findAll() {
        try (InputStream inputStream = resourceLoader.getResource(servicesConfigPath).getInputStream()) {
            return objectMapper.readValue(inputStream, new TypeReference<>() {});
        } catch (IOException e) {
            System.err.println("Error loading Intnet services configuration: " + e.getMessage());
            return null;
        }
    }

    public List<IntnetService> findByNames(List<String> serviceNames) {
        List<IntnetService> allServices = findAll();
        if (allServices == null) {
            return null;
        }
        return allServices.stream()
                .filter(service -> serviceNames.contains(service.getName()))
                .toList();
    }
}
