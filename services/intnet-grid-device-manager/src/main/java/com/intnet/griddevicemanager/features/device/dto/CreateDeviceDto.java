package com.intnet.griddevicemanager.features.device.dto;

import com.intnet.griddevicemanager.features.device.model.DeviceDataConfig;
import com.intnet.griddevicemanager.features.device.model.DeviceMetadata;
import com.intnet.griddevicemanager.features.device.model.DeviceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateDeviceDto {

    private String ipAddress;
    private String protocol;
    private DeviceDataConfig deviceDataConfig;
    private DeviceStatus status;
    private Double latitude;
    private Double longitude;
    private DeviceMetadata metadata;
}
