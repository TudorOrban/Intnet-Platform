package com.intnet.griddata.features.generator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneratorStateSearchDto {

    private Long id;
    private Long gridId;
    private ZonedDateTime updatedAt;
    private Double activePowerGeneration;
    private Double reactivePowerGeneration;
    private Double generatorVoltageSetpoint;
}
