package com.intnet.griddata.features.generator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateGeneratorStateDto {

    private Long generatorId;
    private Double activePowerGeneration;
    private Double reactivePowerGeneration;
    private Double generatorVoltageSetpoint;
}
