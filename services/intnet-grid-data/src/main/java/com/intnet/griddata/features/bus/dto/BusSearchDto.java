package com.intnet.griddata.features.bus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusSearchDto {

    private Long id;
    private Long gridId;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private Double latitude;
    private Double longitude;

    private BusStateDto state;
}
