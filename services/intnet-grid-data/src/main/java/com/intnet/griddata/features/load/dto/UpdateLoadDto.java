package com.intnet.griddata.features.load.dto;

import com.intnet.griddata.features.load.model.LoadType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateLoadDto {

    private Long id;
    private String loadName;
    private LoadType loadType;
}
