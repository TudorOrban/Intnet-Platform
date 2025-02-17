package com.intnet.griddata.core.internal.out.gridtopology.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GridGraphFullDto {
    private Long id;
    private Long graphId;
    private GridGraph graph;
}
