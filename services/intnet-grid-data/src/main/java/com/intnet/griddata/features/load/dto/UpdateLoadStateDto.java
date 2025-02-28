package com.intnet.griddata.features.load.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateLoadStateDto {

    private Long loadId;
    private Double activePowerLoad;
    private Double reactivePowerLoad;
}
