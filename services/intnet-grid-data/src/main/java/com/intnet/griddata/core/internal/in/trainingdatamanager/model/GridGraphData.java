package com.intnet.griddata.core.internal.in.trainingdatamanager.model;

import com.intnet.griddata.features.bus.dto.BusSearchDto;
import com.intnet.griddata.features.edge.dto.EdgeSearchDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class GridGraphData {
    private List<BusSearchDto> buses;
    private List<EdgeSearchDto> edges;
}
