package com.intnet.griddata.core.internal.out.gridtopology.service;

import com.intnet.griddata.core.internal.out.gridtopology.dto.*;
import com.intnet.griddata.core.internal.out.gridtopology.model.GridGraph;

public interface GridTopologyCommunicatorService {

    GridGraph getGraphByGridId(Long gridId);
    void createGraph(CreateGridGraphDto graphDto);
    GridNodeDto addNode(AddGridNodeDto nodeDto);
    GridEdgeDto addEdge(AddGridEdgeDto edgeDto);
}
