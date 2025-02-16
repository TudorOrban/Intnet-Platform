package com.intnet.network.features.graph.service;

import com.intnet.network.features.graph.dto.CreateGridGraphDto;
import com.intnet.network.features.graph.model.GridGraph;

public interface NetworkGraphService {

    void createGraph(CreateGridGraphDto graphDto);
    GridGraph loadGraphFromNeo4j(Long graphId, Boolean includeAdjacencyMap);
}
