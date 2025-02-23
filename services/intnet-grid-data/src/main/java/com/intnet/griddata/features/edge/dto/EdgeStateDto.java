package com.intnet.griddata.features.edge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EdgeStateDto {

    private Long id;
    private Long gridId;
    private Long edgeId;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private Double lineSwitchingStatus;
}
