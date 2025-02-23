package com.intnet.gridhistory.features.bus.dto;

import com.intnet.gridhistory.features.bus.model.BusStateRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BusStateRecordMapper {
    BusStateRecordMapper INSTANCE = Mappers.getMapper(BusStateRecordMapper.class);

    @Mapping(source = "record.id", target = "id")
    @Mapping(source = "record.gridId", target = "gridId")
    @Mapping(source = "record.busId", target = "busId")
    @Mapping(source = "record.time", target = "time")
    @Mapping(source = "record.voltageMagnitude", target = "voltageMagnitude")
    @Mapping(source = "record.voltageAngle", target = "voltageAngle")
    @Mapping(source = "record.activePowerInjection", target = "activePowerInjection")
    @Mapping(source = "record.reactivePowerInjection", target = "reactivePowerInjection")
    @Mapping(source = "record.shuntCapacitorReactorStatus", target = "shuntCapacitorReactorStatus")
    @Mapping(source = "record.phaseShiftingTransformerTapPosition", target = "phaseShiftingTransformerTapPosition")
    BusStateRecordDto mapBusStateRecordToBusStateRecordDto(BusStateRecord record);

    @Mapping(source = "gridId", target = "gridId")
    @Mapping(source = "busId", target = "busId")
    @Mapping(source = "record.voltageMagnitude", target = "voltageMagnitude")
    @Mapping(source = "record.voltageAngle", target = "voltageAngle")
    @Mapping(source = "record.activePowerInjection", target = "activePowerInjection")
    @Mapping(source = "record.reactivePowerInjection", target = "reactivePowerInjection")
    @Mapping(source = "record.shuntCapacitorReactorStatus", target = "shuntCapacitorReactorStatus")
    @Mapping(source = "record.phaseShiftingTransformerTapPosition", target = "phaseShiftingTransformerTapPosition")
    BusStateRecord mapCreateBusStateRecordDtoToBusStateRecord(CreateBusStateRecordDto recordDto);
}
