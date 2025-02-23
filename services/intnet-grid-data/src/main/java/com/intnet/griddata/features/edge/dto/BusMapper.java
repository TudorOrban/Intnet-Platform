package com.intnet.griddata.features.edge.dto;

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
    @Mapping(source = "bus.busName", target = "busName")
    @Mapping(source = "bus.createdAt", target = "createdAt")
    @Mapping(source = "bus.updatedAt", target = "updatedAt")
    @Mapping(source = "bus.latitude", target = "latitude")
    @Mapping(source = "bus.longitude", target = "longitude")
    @Mapping(target = "generators", ignore = true)
    EdgeSearchDto busToBusSearchDto(Bus bus);

    @Mapping(source = "gridId", target = "gridId")
    @Mapping(source = "latitude", target = "latitude")
    @Mapping(source = "longitude", target = "longitude")
    Bus createBusDtoToBus(CreateEdgeDto busDto);

    @Mapping(source = "state.id", target = "id")
    @Mapping(source = "state.gridId", target = "gridId")
    @Mapping(source = "state.updatedAt", target = "updatedAt")
    @Mapping(source = "state.voltageMagnitude", target = "voltageMagnitude")
    @Mapping(source = "state.voltageAngle", target = "voltageAngle")
    @Mapping(source = "state.activePowerInjection", target = "activePowerInjection")
    @Mapping(source = "state.reactivePowerInjection", target = "reactivePowerInjection")
    @Mapping(source = "state.shuntCapacitorReactorStatus", target = "shuntCapacitorReactorStatus")
    @Mapping(source = "state.phaseShiftingTransformerTapPosition", target = "phaseShiftingTransformerTapPosition")
    EdgeStateDto busStateToBusStateDto(BusState state);
}