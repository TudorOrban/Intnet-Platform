package com.intnet.gridtopology.features.graph.dto;

import com.intnet.gridtopology.features.graph.model.GridGraph;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateGridGraphDto {
    private Long graphId;
    private GridGraph graph;
}
