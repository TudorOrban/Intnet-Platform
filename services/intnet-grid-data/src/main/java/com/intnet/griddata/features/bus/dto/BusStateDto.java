package com.intnet.griddata.features.bus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusStateDto {

    private Long id;
    private Long gridId;
    private Long busId;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private Double voltage;
    private Double load;
    private Double generation;
    private Double phaseAngle;
}
