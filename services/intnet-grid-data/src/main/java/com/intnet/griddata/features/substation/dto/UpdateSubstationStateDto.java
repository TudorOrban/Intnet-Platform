package com.intnet.griddata.features.substation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSubstationStateDto {

    private Long substationId;
    private Double voltage;
    private Double current;
    private Double frequency;
    private Double temperature;
    private Double load;
    private Double totalInflow;
    private Double totalOutflow;
}
