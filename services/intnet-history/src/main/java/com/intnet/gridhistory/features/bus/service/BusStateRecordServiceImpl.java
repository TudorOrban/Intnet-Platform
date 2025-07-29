package com.intnet.gridhistory.features.bus.service;

import com.intnet.gridhistory.features.bus.dto.BusStateRecordMapper;
import com.intnet.gridhistory.features.bus.dto.CreateBusStateRecordDto;
import com.intnet.gridhistory.features.bus.dto.GetBusRecordsRequestDto;
import com.intnet.gridhistory.features.bus.model.BusStateRecord;
import com.intnet.gridhistory.features.bus.dto.BusStateRecordDto;
import com.intnet.gridhistory.features.bus.repository.BusStateRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * Service responsible for CRUD ops for Bus State Record entity
 */
@Service
public class BusStateRecordServiceImpl implements BusStateRecordService {

    private final BusStateRecordRepository recordRepository;

    @Autowired
    public BusStateRecordServiceImpl(
            BusStateRecordRepository recordRepository
    ) {
        this.recordRepository = recordRepository;
    }

    public List<BusStateRecordDto> getRecordsByBusIdAndPeriod(GetBusRecordsRequestDto requestDto) {
        List<BusStateRecord> records = recordRepository.findByBusIdAndTimeBetween(
                requestDto.getBusId(), requestDto.getStartTime(), requestDto.getEndTime()
        );

        return records.stream().map(this::mapRecordToRecordDto).toList();
    }

    public BusStateRecordDto createRecord(CreateBusStateRecordDto recordDto) {
        BusStateRecord record = this.mapCreateBusStateRecordDtoToBusStateRecord(recordDto);

        BusStateRecord savedRecord = recordRepository.save(record);

        return this.mapRecordToRecordDto(savedRecord);
    }

    public List<BusStateRecordDto> createRecordsInBulk(List<CreateBusStateRecordDto> recordDtos) {
        List<BusStateRecord> records = recordDtos.stream().map(this::mapCreateBusStateRecordDtoToBusStateRecord).toList();

        List<BusStateRecord> savedRecords = recordRepository.saveAll(records);

        return savedRecords.stream().map(this::mapRecordToRecordDto).toList();
    }

    private BusStateRecordDto mapRecordToRecordDto(BusStateRecord record) {
        return BusStateRecordMapper.INSTANCE.mapBusStateRecordToBusStateRecordDto(record);
    }

    private BusStateRecord mapCreateBusStateRecordDtoToBusStateRecord(CreateBusStateRecordDto recordDto) {
        return BusStateRecordMapper.INSTANCE.mapCreateBusStateRecordDtoToBusStateRecord(recordDto);
    }
}
