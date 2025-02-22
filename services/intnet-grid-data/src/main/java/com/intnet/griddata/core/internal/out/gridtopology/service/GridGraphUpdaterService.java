package com.intnet.griddata.core.internal.out.gridtopology.service;

import com.intnet.griddata.core.internal.out.gridtopology.dto.GridNodeDto;
import com.intnet.griddata.features.bus.model.Bus;

public interface GridGraphUpdaterService {

    GridNodeDto updateGraph(Bus newBus);
}
