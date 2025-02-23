package com.intnet.griddata.features.generator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateGeneratorDto {

    private Long id;
    private String generatorName;
    private Double generatorVoltageSetpoint;
    private Double generatorMaxActivePower;
    private Double generatorMinActivePower;
    private Double generatorMaxReactivePower;
    private Double generatorMinReactivePower;
}
