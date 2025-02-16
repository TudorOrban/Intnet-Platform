package com.intnet.network.features.graph.service;

import com.intnet.network.features.graph.model.GridGraph;

public interface NetworkGraphService {

    void saveGraphToNeo4j(GridGraph graph, Long graphId);
    GridGraph loadGraphFromNeo4j(Long graphId, Boolean includeAdjacencyMap);
}
