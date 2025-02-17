package com.intnet.griddata.features.bus.controller;

import com.intnet.griddata.features.bus.dto.BusStateDto;
import com.intnet.griddata.features.bus.dto.UpdateBusStateDto;
import com.intnet.griddata.features.bus.service.BusStateUpdaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/buses/state")
public class BusStateUpdaterController {

    private final BusStateUpdaterService stateUpdaterService;

    @Autowired
    public BusStateUpdaterController(
            BusStateUpdaterService stateUpdaterService
    ) {
        this.stateUpdaterService = stateUpdaterService;
    }

    @PutMapping
    public ResponseEntity<BusStateDto> updateBusState(@RequestBody UpdateBusStateDto stateDto) {
        BusStateDto updatedState = stateUpdaterService.updateBusState(stateDto);
        return ResponseEntity.ok(updatedState);
    }
}
