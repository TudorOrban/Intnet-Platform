package com.intnet.griddata.core.grid.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateGridDto {
    private String name;
    private String description;
}
