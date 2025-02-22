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
    @Mapping(source = "record.voltage", target = "voltage")
    @Mapping(source = "record.load", target = "load")
    @Mapping(source = "record.generation", target = "generation")
    @Mapping(source = "record.phaseAngle", target = "phaseAngle")
    BusStateRecordDto mapBusStateRecordToBusStateRecordDto(BusStateRecord record);

    @Mapping(source = "gridId", target = "gridId")
    @Mapping(source = "busId", target = "busId")
    @Mapping(source = "voltage", target = "voltage")
    @Mapping(source = "load", target = "load")
    @Mapping(source = "generation", target = "generation")
    @Mapping(source = "phaseAngle", target = "phaseAngle")
    BusStateRecord mapCreateBusStateRecordDtoToBusStateRecord(CreateBusStateRecordDto recordDto);
}
