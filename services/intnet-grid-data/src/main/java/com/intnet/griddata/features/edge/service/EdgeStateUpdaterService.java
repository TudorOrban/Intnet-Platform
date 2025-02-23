package com.intnet.griddata.features.edge.service;

import com.intnet.griddata.features.edge.dto.EdgeStateDto;
import com.intnet.griddata.features.edge.dto.UpdateEdgeStateDto;

public interface EdgeStateUpdaterService {

    EdgeStateDto updateEdgeState(UpdateEdgeStateDto stateDto);
}
