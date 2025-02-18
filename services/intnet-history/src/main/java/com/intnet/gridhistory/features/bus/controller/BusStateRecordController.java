package com.intnet.gridhistory.features.bus.controller;

import com.intnet.gridhistory.features.bus.dto.CreateBusStateRecordDto;
import com.intnet.gridhistory.features.bus.dto.GetBusRecordsRequestDto;
import com.intnet.gridhistory.features.bus.model.BusStateRecordDto;
import com.intnet.gridhistory.features.bus.service.BusStateRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/bus-state-records")
public class BusStateRecordController {

    private final BusStateRecordService recordService;

    @Autowired
    public BusStateRecordController(BusStateRecordService recordService) {
        this.recordService = recordService;
    }

    @GetMapping("/{busId}")
    public ResponseEntity<List<BusStateRecordDto>> getRecordsByBusIdAndPeriod(
            @PathVariable Long busId,
            @RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime startTime,
            @RequestParam("endTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime endTime
    ) {
        GetBusRecordsRequestDto requestDto = new GetBusRecordsRequestDto(busId, startTime, endTime);
        List<BusStateRecordDto> records = recordService.getRecordsByBusIdAndPeriod(requestDto);
        return ResponseEntity.ok(records);
    }

    @PostMapping
    public ResponseEntity<BusStateRecordDto> createRecord(@RequestBody CreateBusStateRecordDto recordDto) {
        BusStateRecordDto savedRecord = recordService.createRecord(recordDto);
        return ResponseEntity.ok(savedRecord);
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<BusStateRecordDto>> createRecordsInBulk(@RequestBody List<CreateBusStateRecordDto> recordDtos) {
        List<BusStateRecordDto> savedRecords = recordService.createRecordsInBulk(recordDtos);
        return ResponseEntity.ok(savedRecords);
    }
}
