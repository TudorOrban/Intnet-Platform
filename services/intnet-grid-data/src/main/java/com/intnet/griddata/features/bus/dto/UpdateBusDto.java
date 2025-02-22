package com.intnet.griddata.features.bus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBusDto {

    private Long id;
    private Long gridId;
    private String busName;
    private Double latitude;
    private Double longitude;
}
