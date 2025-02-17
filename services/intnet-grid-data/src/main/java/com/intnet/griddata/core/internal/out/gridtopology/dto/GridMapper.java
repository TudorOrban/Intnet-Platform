package com.intnet.griddata.core.internal.out.gridtopology.dto;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GridMapper {
    GridMapper INSTANCE = Mappers.getMapper(GridMapper.class);

//    @Mapping(source = "node.id", target = "id")
//    @Mapping(source = "node.gridId", target = "gridId")
//    @Mapping(source = "node.nodeType", target = "nodeType")
//    GridNodeDto gridNodeToGridNodeDto(GridNode node);
//
//    @Mapping(source = "id", target = "id")
//    @Mapping(source = "gridId", target = "gridId")
//    @Mapping(source = "nodeType", target = "nodeType")
//    GridNode addGridNodeDtoToGridNode(AddGridNodeDto nodeDto);
//
//    @Mapping(source = "edge.id", target = "id")
//    @Mapping(source = "edge.sourceNodeId", target = "sourceNodeId")
//    @Mapping(source = "edge.sourceNodeType", target = "sourceNodeType")
//    @Mapping(source = "edge.destinationNodeId", target = "destinationNodeId")
//    @Mapping(source = "edge.destinationNodeType", target = "destinationNodeType")
//    @Mapping(source = "edge.edgeType", target = "edgeType")
//    GridEdgeDto gridEdgeToGridEdgeDto(GridEdge edge);
//
//    @Mapping(source = "id", target = "id")
//    @Mapping(source = "sourceNodeId", target = "sourceNodeId")
//    @Mapping(source = "sourceNodeType", target = "sourceNodeType")
//    @Mapping(source = "destinationNodeId", target = "destinationNodeId")
//    @Mapping(source = "destinationNodeType", target = "destinationNodeType")
//    @Mapping(source = "edgeType", target = "edgeType")
//    GridEdge addGridEdgeDtoToGridEdge(AddGridEdgeDto edgeDto);
}
