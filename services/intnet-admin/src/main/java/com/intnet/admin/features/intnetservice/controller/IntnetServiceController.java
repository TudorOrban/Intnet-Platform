package com.intnet.admin.features.intnetservice.controller;

import com.intnet.admin.features.intnetservice.model.IntnetService;
import com.intnet.admin.features.intnetservice.model.PodData;
import com.intnet.admin.features.intnetservice.model.ServiceKubernetesData;
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
    public ResponseEntity<List<IntnetService>> getIntnetServices(
            @RequestParam(required = false, defaultValue = "default") String namespace
    ) {
        List<IntnetService> services = serviceManager.getServices(namespace);
        return ResponseEntity.ok(services);
    }

    @GetMapping("/{serviceName}/pods")
    public ResponseEntity<ServiceKubernetesData> getPodsForIntnetService(
            @PathVariable String serviceName,
            @RequestParam(required = false, defaultValue = "default") String namespace
    ) {
        ServiceKubernetesData data = serviceManager.getServiceWithPods(serviceName, namespace);
        return ResponseEntity.ok(data);
    }

    @GetMapping(value = "/{serviceName}/pods/{podName}/logs", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<DataBuffer> streamPodLogs(
            @PathVariable String serviceName,
            @PathVariable String podName,
            @RequestParam(required = false, defaultValue = "default") String namespace,
            @RequestParam(required = false) String containerName
    ) {
        return serviceManager.streamPodLogs(podName, namespace, containerName);
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
