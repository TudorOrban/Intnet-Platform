package com.intnet.griddata.features.substation.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubstationSearchDto {

    private Long id;
    private Long gridId;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private Double latitude;
    private Double longitude;
    private Integer transformers;
    private List<Integer> connectedBuses;
    private ZonedDateTime operationalSince;

}
