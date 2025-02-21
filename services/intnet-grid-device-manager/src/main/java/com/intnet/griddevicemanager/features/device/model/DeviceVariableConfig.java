package com.intnet.griddevicemanager.features.device.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceVariableConfig {

    private RegisterType type;
    private String address;
    private String dataType;
    private Float scalingFactor;
    private Float pollFrequencySeconds;
}
