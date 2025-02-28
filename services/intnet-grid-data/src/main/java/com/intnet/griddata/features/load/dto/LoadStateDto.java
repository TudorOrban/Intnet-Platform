package com.intnet.griddata.features.load.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoadStateDto {

    private Long id;
    private Long gridId;
    private ZonedDateTime updatedAt;
    private Double activePowerLoad;
    private Double reactivePowerLoad;
}
