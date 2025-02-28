package com.intnet.griddata.features.load.dto;

import com.intnet.griddata.features.load.model.LoadType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoadSearchDto {

    private Long id;
    private Long gridId;
    private String loadName;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private LoadType loadType;

    private LoadStateDto state;
}
