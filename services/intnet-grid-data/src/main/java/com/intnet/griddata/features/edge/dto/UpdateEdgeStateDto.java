package com.intnet.griddata.features.edge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEdgeStateDto {

    private Long edgeId;
    private Boolean lineSwitchingStatus;
}
