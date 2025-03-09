package com.intnet.admin.features.intnetservice.service;

import com.intnet.admin.features.intnetservice.model.IntnetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class ImageBuilderServiceImpl implements ImageBuilderService {

    private final WebClient webClient;

    @Autowired
    public ImageBuilderServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://host.docker.internal:8080/api/v1/build-images").build();
    }

    public void buildImages(List<IntnetService> services) {
        webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(services)
                .retrieve().bodyToMono(String.class).block();
    }
}
