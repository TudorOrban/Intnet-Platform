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
    private Double voltageMagnitude;
    private Double voltageAngle;
    private Double activePowerInjection;
    private Double reactivePowerInjection;
    private Boolean shuntCapacitorReactorStatus;
    private Double phaseShiftingTransformerTapPosition;
}
