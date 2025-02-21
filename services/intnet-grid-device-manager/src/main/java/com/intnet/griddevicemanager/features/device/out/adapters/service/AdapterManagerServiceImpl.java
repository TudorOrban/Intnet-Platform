package com.intnet.griddevicemanager.features.device.out.adapters.service;

import com.intnet.griddevicemanager.features.device.dto.DeviceSearchDto;
import com.intnet.griddevicemanager.features.device.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class AdapterManagerServiceImpl implements AdapterManagerService {

    private final DeviceService deviceService;
    private final RestTemplate restTemplate;

    @Value("${modbus-adapter.service.url}")
    private String modbusAdapterServiceUrl;

    @Autowired
    public AdapterManagerServiceImpl(
            DeviceService deviceService,
            RestTemplate restTemplate
    ) {
        this.deviceService = deviceService;
        this.restTemplate = restTemplate;
    }

    public void startAdapters() {
        List<DeviceSearchDto> devices = deviceService.getDevices(true);

        try {
            restTemplate.postForObject(modbusAdapterServiceUrl + "/start-connection", devices, Void.class);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while attempting to start device connections: " + e.getMessage());
        }
    }
}
