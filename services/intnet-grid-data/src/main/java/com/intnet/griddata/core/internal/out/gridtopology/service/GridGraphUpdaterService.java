package com.intnet.griddata.core.internal.out.gridtopology.service;

import com.intnet.griddata.core.internal.out.gridtopology.dto.GridEdgeDto;
import com.intnet.griddata.core.internal.out.gridtopology.dto.GridNodeDto;
import com.intnet.griddata.features.bus.model.Bus;
import com.intnet.griddata.features.edge.model.Edge;

public interface GridGraphUpdaterService {

    GridNodeDto updateGraph(Bus newBus);
    GridEdgeDto updateGraph(Edge newEdge);
}
