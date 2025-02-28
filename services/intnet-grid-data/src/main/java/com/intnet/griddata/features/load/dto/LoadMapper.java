package com.intnet.griddata.features.load.dto;

import com.intnet.griddata.features.load.model.Load;
import com.intnet.griddata.features.load.model.LoadState;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LoadMapper {
    LoadMapper INSTANCE = Mappers.getMapper(LoadMapper.class);

    @Mapping(source = "load.id", target = "id")
    @Mapping(source = "load.gridId", target = "gridId")
    @Mapping(source = "load.loadName", target = "loadName")
    @Mapping(source = "load.createdAt", target = "createdAt")
    @Mapping(source = "load.updatedAt", target = "updatedAt")
    @Mapping(source = "load.loadType", target = "loadType")
    LoadSearchDto loadToLoadSearchDto(Load load);

    @Mapping(source = "gridId", target = "gridId")
    @Mapping(source = "loadName", target = "loadName")
    @Mapping(source = "loadType", target = "loadType")
    Load createLoadDtoToLoad(CreateLoadDto loadDto);

    @Mapping(source = "state.id", target = "id")
    @Mapping(source = "state.gridId", target = "gridId")
    @Mapping(source = "state.updatedAt", target = "updatedAt")
    @Mapping(source = "state.activePowerLoad", target = "activePowerLoad")
    @Mapping(source = "state.reactivePowerLoad", target = "reactivePowerLoad")
    LoadStateDto stateToStateSearchDto(LoadState state);
}
