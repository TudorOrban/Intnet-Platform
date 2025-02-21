package com.intnet.griddevicemanager.features.device.controller;

import com.intnet.griddevicemanager.features.device.dto.CreateDeviceDto;
import com.intnet.griddevicemanager.features.device.dto.DeviceSearchDto;
import com.intnet.griddevicemanager.features.device.dto.UpdateDeviceDto;
import com.intnet.griddevicemanager.features.device.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/devices")
public class DeviceController {

    private final DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping()
    public ResponseEntity<List<DeviceSearchDto>> getDevices(
            @RequestParam(required = false, defaultValue = "false") Boolean includeAssociations
    ) {
        List<DeviceSearchDto> devices = deviceService.getDevices(includeAssociations);
        return ResponseEntity.ok(devices);
    }

    @PostMapping
    public ResponseEntity<DeviceSearchDto> createDevice(@RequestBody CreateDeviceDto deviceDto) {
        DeviceSearchDto savedDevice = deviceService.createDevice(deviceDto);
        return ResponseEntity.ok(savedDevice);
    }

    @PutMapping
    public ResponseEntity<DeviceSearchDto> updateDevice(@RequestBody UpdateDeviceDto deviceDto) {
        DeviceSearchDto updatedDevice = deviceService.updateDevice(deviceDto);
        return ResponseEntity.ok(updatedDevice);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable Long id) {
        deviceService.deleteDevice(id);
        return ResponseEntity.ok().build();
    }
}
