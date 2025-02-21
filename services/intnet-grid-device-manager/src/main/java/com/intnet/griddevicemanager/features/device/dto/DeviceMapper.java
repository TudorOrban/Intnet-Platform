package com.intnet.griddevicemanager.features.device.dto;

import com.intnet.griddevicemanager.features.device.model.Device;
import com.intnet.griddevicemanager.features.device.model.GridElementAssociation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DeviceMapper {
    DeviceMapper INSTANCE = Mappers.getMapper(DeviceMapper.class);

    @Mapping(source = "device.id", target = "id")
    @Mapping(source = "device.ipAddress", target = "ipAddress")
    @Mapping(source = "device.protocol", target = "protocol")
    @Mapping(source = "device.deviceDataConfig", target = "deviceDataConfig")
    @Mapping(source = "device.status", target = "status")
    @Mapping(source = "device.createdAt", target = "createdAt")
    @Mapping(source = "device.updatedAt", target = "updatedAt")
    @Mapping(source = "device.latitude", target = "latitude")
    @Mapping(source = "device.longitude", target = "longitude")
    @Mapping(source = "device.metadata", target = "metadata")
    DeviceSearchDto mapDeviceToDeviceSearchDto(Device device);

    @Mapping(source = "ipAddress", target = "ipAddress")
    @Mapping(source = "protocol", target = "protocol")
    @Mapping(source = "deviceDataConfig", target = "deviceDataConfig")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "latitude", target = "latitude")
    @Mapping(source = "longitude", target = "longitude")
    @Mapping(source = "metadata", target = "metadata")
    Device mapCreateDeviceDtoToDevice(CreateDeviceDto deviceDto);

    @Mapping(source = "association.id", target = "id")
    @Mapping(source = "association.deviceId", target = "deviceId")
    @Mapping(source = "association.gridElementId", target = "gridElementId")
    @Mapping(source = "association.elementType", target = "elementType")
    AssociationSearchDto mapAssociationToAssociationSearchDto(GridElementAssociation association);

    @Mapping(source = "deviceId", target = "deviceId")
    @Mapping(source = "gridElementId", target = "gridElementId")
    @Mapping(source = "elementType", target = "elementType")
    GridElementAssociation mapCreateAssociationDtoToAssociate(CreateAssociationDto associationDto);
}
