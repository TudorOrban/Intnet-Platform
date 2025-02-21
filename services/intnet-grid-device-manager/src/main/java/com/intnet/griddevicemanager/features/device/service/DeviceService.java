package com.intnet.griddevicemanager.features.device.service;

import com.intnet.griddevicemanager.features.device.dto.CreateDeviceDto;
import com.intnet.griddevicemanager.features.device.dto.DeviceSearchDto;
import com.intnet.griddevicemanager.features.device.dto.UpdateDeviceDto;

import java.util.List;

public interface DeviceService {

    List<DeviceSearchDto> getDevices(Boolean includeAssociations);
    DeviceSearchDto createDevice(CreateDeviceDto deviceDto);
    DeviceSearchDto updateDevice(UpdateDeviceDto updateDeviceDto);
    void deleteDevice(Long id);
}
