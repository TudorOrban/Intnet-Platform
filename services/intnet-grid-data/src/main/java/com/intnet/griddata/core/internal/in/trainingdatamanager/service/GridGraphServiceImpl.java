package com.intnet.griddata.core.internal.in.trainingdatamanager.service;

import com.intnet.griddata.core.internal.in.trainingdatamanager.model.GridGraph;
import com.intnet.griddata.core.internal.in.trainingdatamanager.model.GridGraphData;
import com.intnet.griddata.features.bus.dto.BusSearchDto;
import com.intnet.griddata.features.bus.service.BusService;
import com.intnet.griddata.features.edge.dto.EdgeSearchDto;
import com.intnet.griddata.features.edge.service.EdgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GridGraphServiceImpl implements GridGraphService {

    private final BusService busService;
    private final EdgeService edgeService;

    @Autowired
    public GridGraphServiceImpl(
            BusService busService,
            EdgeService edgeService
    ) {
        this.busService = busService;
        this.edgeService = edgeService;
    }

    public GridGraph getGridGraphByGridId(Long gridId) {
        List<BusSearchDto> buses = busService.getBusesByGridId(gridId, true);
        List<EdgeSearchDto> edges = edgeService.getEdgesByGridId(gridId, true);

        return new GridGraph(gridId, new GridGraphData(buses, edges));
    }
}
