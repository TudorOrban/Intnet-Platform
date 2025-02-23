package com.intnet.gridtopology.features.graph.internal.in.griddata.dto;

import com.intnet.gridtopology.features.graph.model.GridEdge;
import com.intnet.gridtopology.features.graph.model.GridNode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GridMapper {
    GridMapper INSTANCE = Mappers.getMapper(GridMapper.class);

    @Mapping(source = "node.id", target = "id")
    @Mapping(source = "node.gridId", target = "gridId")
    @Mapping(source = "node.nodeName", target = "nodeName")
    GridNodeDto gridNodeToGridNodeDto(GridNode node);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "gridId", target = "gridId")
    @Mapping(source = "nodeName", target = "nodeName")
    GridNode addGridNodeDtoToGridNode(AddGridNodeDto nodeDto);

    @Mapping(source = "edge.id", target = "id")
    @Mapping(source = "edge.gridId", target = "gridId")
    @Mapping(source = "edge.edgeName", target = "edgeName")
    @Mapping(source = "edge.srcNodeId", target = "srcNodeId")
    @Mapping(source = "edge.destNodeId", target = "destNodeId")
    @Mapping(source = "edge.edgeType", target = "edgeType")
    GridEdgeDto gridEdgeToGridEdgeDto(GridEdge edge);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "gridId", target = "gridId")
    @Mapping(source = "edgeName", target = "edgeName")
    @Mapping(source = "srcNodeId", target = "srcNodeId")
    @Mapping(source = "destNodeId", target = "destNodeId")
    @Mapping(source = "edgeType", target = "edgeType")
    GridEdge addGridEdgeDtoToGridEdge(AddGridEdgeDto edgeDto);
}
