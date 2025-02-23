package com.intnet.griddata.features.edge.dto;

import com.intnet.griddata.shared.enums.EdgeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EdgeSearchDto {

    private Long id;
    private Long gridId;
    private String edgeName;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private Long srcBusId;
    private Long destBusId;
    private EdgeType edgeType;
    private Double lineLength;
    private Double resistance;
    private Double reactance;
    private Double conductance;
    private Double susceptance;
    private Double thermalRating;
    private Double voltageLimitsMin;
    private Double voltageLimitsMax;

    private EdgeStateDto state;

//    private List<GeneratorSearchDto> generators;
}
