package com.intnet.griddata.features.transmissionline.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransmissionLineStateDto {

    private Long id;
    private Long gridId;
    private Long transmissionLineId;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private Double current;
    private Double powerFlowActive;
    private Double powerFlowReactive;
    private Double temperature;
}
