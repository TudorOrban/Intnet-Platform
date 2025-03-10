package com.intnet.admin.features.intnetservice.service;

import com.intnet.admin.features.intnetservice.model.IntnetService;
import org.springframework.core.io.buffer.DataBuffer;
import reactor.core.publisher.Flux;

import java.util.List;

public interface ImageBuilderService {

    Flux<DataBuffer> buildImages(List<IntnetService> services);
}
