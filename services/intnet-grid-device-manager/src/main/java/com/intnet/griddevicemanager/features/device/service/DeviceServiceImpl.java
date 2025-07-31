package com.intnet.griddevicemanager.features.device.service;

import com.intnet.griddevicemanager.features.device.dto.*;
import com.intnet.griddevicemanager.features.device.model.Device;
import com.intnet.griddevicemanager.features.device.model.GridElementAssociation;
import com.intnet.griddevicemanager.features.device.repository.DeviceRepository;
import com.intnet.griddevicemanager.features.device.repository.GridElementAssociationRepository;
import com.intnet.griddevicemanager.shared.exception.types.ResourceIdentifierType;
import com.intnet.griddevicemanager.shared.exception.types.ResourceNotFoundException;
import com.intnet.griddevicemanager.shared.exception.types.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/*
 * Service responsible for managing grid devices
 */
@Service
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;
    private final GridElementAssociationRepository associationRepository;

    @Autowired
    public DeviceServiceImpl(
            DeviceRepository deviceRepository,
            GridElementAssociationRepository associationRepository
    ) {
        this.deviceRepository = deviceRepository;
        this.associationRepository = associationRepository;
    }

    public List<DeviceSearchDto> getDevices(Boolean includeAssociations) {
        List<Device> devices = deviceRepository.findAll();
        List<DeviceSearchDto> deviceDtos = devices.stream().map(this::mapDeviceToDeviceSearchDto).toList();

        if (includeAssociations) {
            this.fetchAndAttachAssociations(deviceDtos);
        }

        return deviceDtos;
    }

    private void fetchAndAttachAssociations(List<DeviceSearchDto> deviceDtos) {
        List<Long> deviceIds = deviceDtos.stream().map(DeviceSearchDto::getId).toList();
        List<GridElementAssociation> associations = associationRepository.findByDeviceIds(deviceIds);
        deviceDtos.forEach(device -> {
            Optional<GridElementAssociation> foundAssociationOpt = associations.stream().filter(a -> Objects.equals(a.getDeviceId(), device.getId())).findAny();
            if (foundAssociationOpt.isEmpty()) {
                return;
            }
            GridElementAssociation foundAssociation = foundAssociationOpt.get();

            AssociationSearchDto associationDto = DeviceMapper.INSTANCE.mapAssociationToAssociationSearchDto(foundAssociation);
            device.setAssociation(associationDto);
        });
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
        device.setDeviceDataConfig(deviceDto.getDeviceDataConfig());
        device.setStatus(deviceDto.getStatus());
        device.setLatitude(deviceDto.getLatitude());
        device.setLongitude(deviceDto.getLongitude());
        device.setMetadata(deviceDto.getMetadata());
    }
}
