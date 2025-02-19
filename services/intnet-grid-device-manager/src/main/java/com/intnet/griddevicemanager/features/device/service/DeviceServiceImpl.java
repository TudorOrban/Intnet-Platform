package com.intnet.griddevicemanager.features.device.service;

import com.intnet.griddevicemanager.features.device.dto.CreateDeviceDto;
import com.intnet.griddevicemanager.features.device.dto.DeviceMapper;
import com.intnet.griddevicemanager.features.device.dto.DeviceSearchDto;
import com.intnet.griddevicemanager.features.device.dto.UpdateDeviceDto;
import com.intnet.griddevicemanager.features.device.model.Device;
import com.intnet.griddevicemanager.features.device.repository.DeviceRepository;
import com.intnet.griddevicemanager.shared.exception.types.ResourceIdentifierType;
import com.intnet.griddevicemanager.shared.exception.types.ResourceNotFoundException;
import com.intnet.griddevicemanager.shared.exception.types.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;

    @Autowired
    public DeviceServiceImpl(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public List<DeviceSearchDto> getDevices() {
        List<Device> devices = deviceRepository.findAll();

        return devices.stream().map(this::mapDeviceToDeviceSearchDto).toList();
    }

    public DeviceSearchDto createDevice(CreateDeviceDto deviceDto) {
        Device device = this.mapCreateDeviceDtoToDevice(deviceDto);

        Device savedDevice = deviceRepository.save(device);

        return this.mapDeviceToDeviceSearchDto(savedDevice);
    }

    public DeviceSearchDto updateDevice(UpdateDeviceDto updateDeviceDto) {
        Device existingDevice = deviceRepository.findById(updateDeviceDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException(updateDeviceDto.getId().toString(), ResourceType.ASSOCIATION, ResourceIdentifierType.ID));

        this.setUpdateDeviceDtoToDevice(existingDevice, updateDeviceDto);

        Device savedDevice = deviceRepository.save(existingDevice);

        return this.mapDeviceToDeviceSearchDto(savedDevice);
    }

    public void deleteDevice(Long id) {
        Device existingDevice = deviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id.toString(), ResourceType.ASSOCIATION, ResourceIdentifierType.ID));

        deviceRepository.delete(existingDevice);
    }

    private DeviceSearchDto mapDeviceToDeviceSearchDto(Device device) {
        return DeviceMapper.INSTANCE.mapDeviceToDeviceSearchDto(device);
    }

    private Device mapCreateDeviceDtoToDevice(CreateDeviceDto deviceDto) {
        return DeviceMapper.INSTANCE.mapCreateDeviceDtoToDevice(deviceDto);
    }

    private void setUpdateDeviceDtoToDevice(Device device, UpdateDeviceDto deviceDto) {
        device.setIpAddress(deviceDto.getIpAddress());
        device.setProtocol(deviceDto.getProtocol());
        device.setDataStructure(deviceDto.getDataStructure());
        device.setStatus(deviceDto.getStatus());
        device.setLatitude(deviceDto.getLatitude());
        device.setLongitude(deviceDto.getLongitude());
        device.setMetadata(deviceDto.getMetadata());
    }
}
