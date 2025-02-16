package com.intnet.network.features.graph.dto;

import com.intnet.network.features.graph.model.GridGraph;
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
