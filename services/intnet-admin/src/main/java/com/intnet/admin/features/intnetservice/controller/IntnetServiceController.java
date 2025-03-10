package com.intnet.admin.features.intnetservice.controller;

import com.intnet.admin.features.intnetservice.model.IntnetService;
import com.intnet.admin.features.intnetservice.service.IntnetServiceManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("api/v1/intnet-services")
public class IntnetServiceController {

    private final IntnetServiceManagerService serviceManager;

    @Autowired
    public IntnetServiceController(IntnetServiceManagerService serviceManager) {
        this.serviceManager = serviceManager;
    }

    @GetMapping
    public ResponseEntity<List<IntnetService>> getIntnetServices(@RequestParam(required = false, defaultValue = "default") String namespace) {
        List<IntnetService> services = serviceManager.getServices(namespace);
        return ResponseEntity.ok(services);
    }

    @PostMapping("/build-images")
    public ResponseEntity<Flux<DataBuffer>> buildServiceImages(@RequestBody List<String> serviceNames) {
        Flux<DataBuffer> logStream = serviceManager.buildServiceImages(serviceNames);
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(logStream);
    }

    @PutMapping("/rollout-restart")
    public ResponseEntity<Void> rolloutRestartDeployments(@RequestBody List<String> serviceNames, @RequestParam(required = false, defaultValue = "default") String namespace) {
        serviceManager.rolloutRestartServiceDeployments(serviceNames, namespace);
        return ResponseEntity.ok().build();
    }
}
