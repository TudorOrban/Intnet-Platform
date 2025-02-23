package com.intnet.griddata.core.internal.out.gridtopology.dto;

import com.intnet.griddata.features.bus.model.Bus;
import com.intnet.griddata.features.edge.model.Edge;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GridMapper {
    GridMapper INSTANCE = Mappers.getMapper(GridMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "gridId", target = "gridId")
    @Mapping(source = "busName", target = "nodeName")
    AddGridNodeDto busToAddGridNodeDto(Bus bus);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "gridId", target = "gridId")
    @Mapping(source = "edgeName", target = "edgeName")
    @Mapping(source = "srcBusId", target = "srcNodeId")
    @Mapping(source = "destBusId", target = "destNodeId")
    @Mapping(source = "edgeType", target = "edgeType")
    AddGridEdgeDto edgeToAddGridEdgeDto(Edge edge);
}
