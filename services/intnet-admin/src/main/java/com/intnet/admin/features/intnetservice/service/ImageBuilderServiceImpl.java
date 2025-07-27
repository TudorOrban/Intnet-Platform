package com.intnet.admin.features.intnetservice.service;

import com.intnet.admin.features.intnetservice.model.IntnetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;

/*
 * Service responsible for building the image of a microservice
 */
@Service
public class ImageBuilderServiceImpl implements ImageBuilderService {

    private final WebClient webClient;

    @Autowired
    public ImageBuilderServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://host.docker.internal:8080/api/v1/build-images").build();
    }

    public Flux<DataBuffer> buildImages(List<IntnetService> services) {
        return webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(services)
                .retrieve()
                .bodyToFlux(DataBuffer.class);
    }
}
