package com.intnet.griddata.features.edge.dto;

import com.intnet.griddata.features.edge.model.Edge;
import com.intnet.griddata.features.edge.model.EdgeState;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EdgeMapper {
    EdgeMapper INSTANCE = Mappers.getMapper(EdgeMapper.class);

    @Mapping(source = "edge.id", target = "id")
    @Mapping(source = "edge.gridId", target = "gridId")
    @Mapping(source = "edge.edgeName", target = "edgeName")
    @Mapping(source = "edge.createdAt", target = "createdAt")
    @Mapping(source = "edge.updatedAt", target = "updatedAt")
    @Mapping(source = "edge.lineLength", target = "lineLength")
    @Mapping(source = "edge.resistance", target = "resistance")
    @Mapping(source = "edge.reactance", target = "reactance")
    @Mapping(source = "edge.conductance", target = "conductance")
    @Mapping(source = "edge.susceptance", target = "susceptance")
    @Mapping(source = "edge.thermalRating", target = "thermalRating")
    @Mapping(source = "edge.voltageLimitsMin", target = "voltageLimitsMin")
    @Mapping(source = "edge.voltageLimitsMax", target = "voltageLimitsMax")
//    @Mapping(target = "transformers", ignore = true)
    EdgeSearchDto edgeToEdgeSearchDto(Edge edge);

    @Mapping(source = "gridId", target = "gridId")
    @Mapping(source = "edgeName", target = "edgeName")
    @Mapping(source = "lineLength", target = "lineLength")
    @Mapping(source = "resistance", target = "resistance")
    @Mapping(source = "reactance", target = "reactance")
    @Mapping(source = "conductance", target = "conductance")
    @Mapping(source = "susceptance", target = "susceptance")
    @Mapping(source = "thermalRating", target = "thermalRating")
    @Mapping(source = "voltageLimitsMin", target = "voltageLimitsMin")
    @Mapping(source = "voltageLimitsMax", target = "voltageLimitsMax")
    Edge createEdgeDtoToEdge(CreateEdgeDto edgeDto);

    @Mapping(source = "state.id", target = "id")
    @Mapping(source = "state.gridId", target = "gridId")
    @Mapping(source = "state.updatedAt", target = "updatedAt")
    @Mapping(source = "state.lineSwitchingStatus", target = "lineSwitchingStatus")
    EdgeStateDto edgeStateToEdgeStateDto(EdgeState state);
}