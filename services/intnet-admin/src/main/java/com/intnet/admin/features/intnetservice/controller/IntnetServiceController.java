package com.intnet.admin.features.intnetservice.controller;

import com.intnet.admin.features.intnetservice.model.IntnetService;
import com.intnet.admin.features.intnetservice.service.IntnetServiceManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<IntnetService>> getIntnetServices() {
        List<IntnetService> services = serviceManager.getServices();
        return ResponseEntity.ok(services);
    }

    @PostMapping("/build-images")
    public ResponseEntity<Void> buildServiceImages(@RequestBody List<String> serviceNames) {
        serviceManager.buildServiceImages(serviceNames);
        return ResponseEntity.ok().build();
    }
}
