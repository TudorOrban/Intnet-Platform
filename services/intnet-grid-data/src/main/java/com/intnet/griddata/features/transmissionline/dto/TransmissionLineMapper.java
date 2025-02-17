package com.intnet.griddata.features.transmissionline.dto;

import com.intnet.griddata.features.transmissionline.model.TransmissionLine;
import com.intnet.griddata.features.transmissionline.model.TransmissionLineState;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TransmissionLineMapper {
    TransmissionLineMapper INSTANCE = Mappers.getMapper(TransmissionLineMapper.class);

    @Mapping(source = "line.id", target = "id")
    @Mapping(source = "line.gridId", target = "gridId")
    @Mapping(source = "line.sourceNodeId", target = "sourceNodeId")
    @Mapping(source = "line.sourceNodeType", target = "sourceNodeType")
    @Mapping(source = "line.destinationNodeId", target = "destinationNodeId")
    @Mapping(source = "line.destinationNodeType", target = "destinationNodeType")
    @Mapping(source = "line.createdAt", target = "createdAt")
    @Mapping(source = "line.updatedAt", target = "updatedAt")
    @Mapping(source = "line.lineType", target = "lineType")
    @Mapping(source = "line.length", target = "length")
    @Mapping(source = "line.impedance", target = "impedance")
    @Mapping(source = "line.admittance", target = "admittance")
    @Mapping(source = "line.capacity", target = "capacity")
    TransmissionLineSearchDto transmissionLineToTransmissionLineSearchDto(TransmissionLine line);

    @Mapping(source = "gridId", target = "gridId")
    @Mapping(source = "sourceNodeId", target = "sourceNodeId")
    @Mapping(source = "sourceNodeType", target = "sourceNodeType")
    @Mapping(source = "destinationNodeId", target = "destinationNodeId")
    @Mapping(source = "destinationNodeType", target = "destinationNodeType")
    @Mapping(source = "lineType", target = "lineType")
    @Mapping(source = "length", target = "length")
    @Mapping(source = "impedance", target = "impedance")
    @Mapping(source = "admittance", target = "admittance")
    @Mapping(source = "capacity", target = "capacity")
    TransmissionLine createTransmissionLineDtoToTransmissionLine(CreateTransmissionLineDto lineDto);

    @Mapping(source = "state.id", target = "id")
    @Mapping(source = "state.gridId", target = "gridId")
    @Mapping(source = "state.transmissionLineId", target = "transmissionLineId")
    @Mapping(source = "state.createdAt", target = "createdAt")
    @Mapping(source = "state.updatedAt", target = "updatedAt")
    @Mapping(source = "state.current", target = "current")
    @Mapping(source = "state.powerFlowActive", target = "powerFlowActive")
    @Mapping(source = "state.powerFlowReactive", target = "powerFlowReactive")
    @Mapping(source = "state.temperature", target = "temperature")
    TransmissionLineStateDto transmissionLineStateToTransmissionLineStateDto(TransmissionLineState state);
}