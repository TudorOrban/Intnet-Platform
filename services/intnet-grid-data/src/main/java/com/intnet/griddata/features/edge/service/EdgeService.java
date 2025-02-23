package com.intnet.griddata.features.edge.service;

import com.intnet.griddata.features.edge.dto.EdgeSearchDto;
import com.intnet.griddata.features.edge.dto.CreateEdgeDto;
import com.intnet.griddata.features.edge.dto.UpdateEdgeDto;

import java.util.List;

public interface EdgeService {

    List<EdgeSearchDto> getEdgesByGridId(Long gridId, Boolean attachComponents);
    EdgeSearchDto getEdgeById(Long id);
    EdgeSearchDto createEdge(CreateEdgeDto edgeDto);
    EdgeSearchDto updateEdge(UpdateEdgeDto edgeDto);
    void deleteEdge(Long id);
}
