package com.intnet.griddata.core.internal.out.gridtopology.dto;

import com.intnet.griddata.features.bus.model.Bus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GridMapper {
    GridMapper INSTANCE = Mappers.getMapper(GridMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "gridId", target = "gridId")
    AddGridNodeDto busToAddGridNodeDto(Bus bus);
}
