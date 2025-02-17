package com.intnet.griddata.core.internal.out.gridtopology.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateGridGraphDto {
    private Long gridId;
    private GridGraph graph;
}
