package com.intnet.griddevicemanager.features.device.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceVariableConfig {

    private String type;
    private Integer address;
    private DataType dataType;
    private Float scalingFactor;
    private Float pollFrequencySeconds;
}
