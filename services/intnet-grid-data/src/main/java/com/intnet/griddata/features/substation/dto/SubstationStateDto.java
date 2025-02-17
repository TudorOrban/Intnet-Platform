package com.intnet.griddata.features.substation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubstationStateDto {

    private Long id;
    private Long gridId;
    private Long substationId;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private Double voltage;
    private Double current;
    private Double frequency;
    private Double temperature;
    private Double load;
    private Double totalInflow;
    private Double totalOutflow;
}
