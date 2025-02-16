package com.intnet.gridtopology.features.graph.service;

import com.intnet.gridtopology.features.graph.dto.CreateGridGraphDto;
import com.intnet.gridtopology.features.graph.model.GridGraph;

public interface GridGraphService {

    void createGraph(CreateGridGraphDto graphDto);
    GridGraph loadGraphFromNeo4j(Long graphId, Boolean includeAdjacencyMap);
}
