package com.intnet.griddevicemanager.features.device.dto;

import com.intnet.griddevicemanager.features.device.model.DeviceDataConfig;
import com.intnet.griddevicemanager.features.device.model.DeviceMetadata;
import com.intnet.griddevicemanager.features.device.model.DeviceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceSearchDto {

    private Long id;
    private String ipAddress;
    private String protocol;
    private DeviceDataConfig deviceDataConfig;
    private DeviceStatus status;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private Double latitude;
    private Double longitude;
    private DeviceMetadata metadata;

    private AssociationSearchDto association;
}
