package com.intnet.gridtopology.features.graph.service;

import com.intnet.gridtopology.features.graph.internal.in.griddata.dto.CreateGridGraphDto;
import com.intnet.gridtopology.features.graph.model.GridGraph;

public interface GridGraphService {

    GridGraph getGraphByGridId(Long gridId, Boolean includeAdjacencyMap);
    void createGraph(CreateGridGraphDto graphDto);
}
