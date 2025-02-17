package com.intnet.gridtopology.features.graph.service;

import com.intnet.gridtopology.features.graph.internal.in.griddata.dto.*;
import com.intnet.gridtopology.features.graph.model.GridGraph;

public interface GridGraphService {

    GridGraph getGraphByGridId(Long gridId);
    void createGraph(CreateGridGraphDto graphDto);
    GridNodeDto addNode(AddGridNodeDto nodeDto);
    GridEdgeDto addEdge(AddGridEdgeDto nodeDto);
}
