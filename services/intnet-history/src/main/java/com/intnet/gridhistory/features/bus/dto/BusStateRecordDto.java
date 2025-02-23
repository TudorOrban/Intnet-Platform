package com.intnet.gridhistory.features.bus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusStateRecordDto {

    private Long id;
    private Long gridId;
    private Long busId;
    private ZonedDateTime time;

    private Double voltageMagnitude;
    private Double voltageAngle;
    private Double activePowerInjection;
    private Double reactivePowerInjection;
    private Boolean shuntCapacitorReactorStatus;
    private Double phaseShiftingTransformerTapPosition;
}
