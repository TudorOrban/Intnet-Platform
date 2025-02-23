package com.intnet.griddata.features.generator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateGeneratorDto {

    private Long gridId;
    private Long busId;
    private String generatorName;
    private Double generatorMaxActivePower;
    private Double generatorMinActivePower;
    private Double generatorMaxReactivePower;
    private Double generatorMinReactivePower;
}
