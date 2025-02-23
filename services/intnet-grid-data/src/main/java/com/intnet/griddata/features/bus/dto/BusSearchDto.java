package com.intnet.griddata.features.bus.dto;

import com.intnet.griddata.features.generator.dto.GeneratorSearchDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusSearchDto {

    private Long id;
    private Long gridId;
    private String busName;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private Double latitude;
    private Double longitude;

    private BusStateDto state;

    private List<GeneratorSearchDto> generators;
}
