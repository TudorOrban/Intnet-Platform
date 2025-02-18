package com.intnet.gridhistory.features.bus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetBusRecordsRequestDto {

    private Long busId;
    private ZonedDateTime startTime;
    private ZonedDateTime endTime;
}
