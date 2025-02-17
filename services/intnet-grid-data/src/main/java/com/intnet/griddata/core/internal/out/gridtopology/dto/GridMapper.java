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

    @Mapping(source = "substation.id", target = "id")
    @Mapping(source = "substation.gridId", target = "gridId")
    AddGridNodeDto substationToAddGridNodeDto(Substation substation);

    @Mapping(source = "bus.id", target = "id")
    @Mapping(source = "bus.gridId", target = "gridId")
    AddGridNodeDto busToAddGridNodeDto(Bus bus);

    @Mapping(source = "line.id", target = "id")
    @Mapping(source = "line.gridId", target = "gridId")
    @Mapping(source = "line.sourceNodeId", target = "sourceNodeId")
    @Mapping(source = "line.sourceNodeType", target = "sourceNodeType")
    @Mapping(source = "line.destinationNodeId", target = "destinationNodeId")
    @Mapping(source = "line.destinationNodeType", target = "destinationNodeType")
    @Mapping(source = "line.edgeType", target = "edgeType")
    AddGridEdgeDto transmissionLineToAddGridEdgeDto(TransmissionLine line);
}
