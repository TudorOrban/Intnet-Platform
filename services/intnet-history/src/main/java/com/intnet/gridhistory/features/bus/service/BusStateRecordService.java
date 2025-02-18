package com.intnet.gridhistory.features.bus.service;

import com.intnet.gridhistory.features.bus.dto.CreateBusStateRecordDto;
import com.intnet.gridhistory.features.bus.dto.GetBusRecordsRequestDto;
import com.intnet.gridhistory.features.bus.model.BusStateRecordDto;

import java.util.List;

public interface BusStateRecordService {

    List<BusStateRecordDto> getRecordsByBusIdAndPeriod(GetBusRecordsRequestDto requestDto);
    BusStateRecordDto createRecord(CreateBusStateRecordDto recordDto);
    List<BusStateRecordDto> createRecordsInBulk(List<CreateBusStateRecordDto> recordDtos);
}
