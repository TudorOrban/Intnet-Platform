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
    private Double voltage;
    private Double load;
    private Double generation;
    private Double phaseAngle;
}
