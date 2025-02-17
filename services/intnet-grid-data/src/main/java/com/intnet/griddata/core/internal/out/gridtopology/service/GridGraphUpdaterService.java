package com.intnet.griddata.core.internal.out.gridtopology.service;

import com.intnet.griddata.core.internal.out.gridtopology.dto.GridEdgeDto;
import com.intnet.griddata.core.internal.out.gridtopology.dto.GridNodeDto;
import com.intnet.griddata.features.bus.model.Bus;
import com.intnet.griddata.features.substation.model.Substation;
import com.intnet.griddata.features.transmissionline.model.TransmissionLine;

public interface GridGraphUpdaterService {

    GridNodeDto updateGraph(Substation newSubstation);
    GridNodeDto updateGraph(Bus newBus);
    GridEdgeDto updateGraph(TransmissionLine newLine);
}
