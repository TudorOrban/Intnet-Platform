package com.intnet.griddata.features.bus.dto;

import com.intnet.griddata.features.bus.model.Bus;
import com.intnet.griddata.features.bus.model.BusState;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BusMapper {
    BusMapper INSTANCE = Mappers.getMapper(BusMapper.class);

    @Mapping(source = "bus.id", target = "id")
    @Mapping(source = "bus.gridId", target = "gridId")
    @Mapping(source = "bus.createdAt", target = "createdAt")
    @Mapping(source = "bus.updatedAt", target = "updatedAt")
    @Mapping(source = "bus.latitude", target = "latitude")
    @Mapping(source = "bus.longitude", target = "longitude")
    BusSearchDto busToBusSearchDto(Bus bus);

    @Mapping(source = "gridId", target = "gridId")
    @Mapping(source = "latitude", target = "latitude")
    @Mapping(source = "longitude", target = "longitude")
    Bus createBusDtoToBus(CreateBusDto busDto);

    @Mapping(source = "state.id", target = "id")
    @Mapping(source = "state.gridId", target = "gridId")
    @Mapping(source = "state.busId", target = "busId")
    @Mapping(source = "state.createdAt", target = "createdAt")
    @Mapping(source = "state.updatedAt", target = "updatedAt")
    @Mapping(source = "state.voltage", target = "voltage")
    @Mapping(source = "state.load", target = "load")
    @Mapping(source = "state.generation", target = "generation")
    @Mapping(source = "state.phaseAngle", target = "phaseAngle")
    BusStateDto busStateToBusStateDto(BusState state);
}