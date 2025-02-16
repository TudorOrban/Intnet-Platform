package com.intnet.griddata.core.grid.dto;

import com.intnet.griddata.core.grid.model.Grid;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GridMapper {
    GridMapper INSTANCE = Mappers.getMapper(GridMapper.class);

    @Mapping(source = "grid.id", target = "id")
    @Mapping(source = "grid.name", target = "name")
    @Mapping(source = "grid.description", target = "description")
    GridSearchDto gridToGridSearchDto(Grid grid);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    Grid gridToGridSearchDto(CreateGridDto gridDto);
}
