package com.intnet.griddata.features.generator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneratorSearchDto {

    private Long id;
    private Long gridId;
    private String generatorName;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private Double generatorVoltageSetpoint;
    private Double generatorMaxActivePower;
    private Double generatorMinActivePower;
    private Double generatorMaxReactivePower;
    private Double generatorMinReactivePower;

    private GeneratorStateDto state;
}
