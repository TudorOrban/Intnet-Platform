package com.intnet.griddata.features.transmissionline.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTransmissionLineStateDto {

    private Long transmissionLineId;
    private Double current;
    private Double powerFlowActive;
    private Double powerFlowReactive;
    private Double temperature;
}
