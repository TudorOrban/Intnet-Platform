package com.intnet.gridhistory.features.bus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBusStateRecordDto {

    private Long gridId;
    private Long busId;
    private Double voltageMagnitude;
    private Double voltageAngle;
    private Double activePowerInjection;
    private Double reactivePowerInjection;
    private Boolean shuntCapacitorReactorStatus;
    private Double phaseShiftingTransformerTapPosition;
}
