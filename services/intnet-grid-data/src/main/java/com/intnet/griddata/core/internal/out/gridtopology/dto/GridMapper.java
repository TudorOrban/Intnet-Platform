package com.intnet.griddata.core.internal.out.gridtopology.dto;

import com.intnet.griddata.features.bus.model.Bus;
import com.intnet.griddata.features.substation.model.Substation;
import com.intnet.griddata.features.transmissionline.model.TransmissionLine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GridMapper {
    GridMapper INSTANCE = Mappers.getMapper(GridMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "gridId", target = "gridId")
    AddGridNodeDto substationToAddGridNodeDto(Substation substation);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "gridId", target = "gridId")
    AddGridNodeDto busToAddGridNodeDto(Bus bus);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "gridId", target = "gridId")
    @Mapping(source = "sourceNodeId", target = "sourceNodeId")
    @Mapping(source = "sourceNodeType", target = "sourceNodeType")
    @Mapping(source = "destinationNodeId", target = "destinationNodeId")
    @Mapping(source = "destinationNodeType", target = "destinationNodeType")
    @Mapping(source = "edgeType", target = "edgeType")
    AddGridEdgeDto transmissionLineToAddGridEdgeDto(TransmissionLine line);
}
