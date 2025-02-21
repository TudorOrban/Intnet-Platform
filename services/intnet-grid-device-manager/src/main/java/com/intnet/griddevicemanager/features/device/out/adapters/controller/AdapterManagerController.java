package com.intnet.griddevicemanager.features.device.out.adapters.controller;

import com.intnet.griddevicemanager.features.device.out.adapters.service.AdapterManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/adapter-manager")
public class AdapterManagerController {

    private final AdapterManagerService managerService;

    @Autowired
    public AdapterManagerController(AdapterManagerService managerService) {
        this.managerService = managerService;
    }

    @PostMapping("/start-adapters")
    public ResponseEntity<Void> startAdapters() {
        this.managerService.startAdapters();
        return ResponseEntity.ok().build();
    }
}
