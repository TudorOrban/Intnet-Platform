package com.intnet.gridtopology.features.graph.internal.in.griddata.dto;

import com.intnet.gridtopology.features.graph.model.GridGraph;
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
