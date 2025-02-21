package com.intnet.griddevicemanager.features.device.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceDataConfig {

    private DeviceVariableConfig voltage;
    private DeviceVariableConfig load;
    private DeviceVariableConfig generation;
    private DeviceVariableConfig phaseAngle;
}
