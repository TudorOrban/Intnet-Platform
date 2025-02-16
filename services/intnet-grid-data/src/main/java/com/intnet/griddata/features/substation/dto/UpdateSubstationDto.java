package com.intnet.griddata.features.substation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSubstationDto {

    private Long id;
    private Long gridId;
    private Double latitude;
    private Double longitude;
    private Integer transformers;
    private List<Integer> connectedBuses;
}
