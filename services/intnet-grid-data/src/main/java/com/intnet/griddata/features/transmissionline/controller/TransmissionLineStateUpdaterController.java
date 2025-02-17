package com.intnet.griddata.features.transmissionline.controller;

import com.intnet.griddata.features.transmissionline.dto.TransmissionLineStateDto;
import com.intnet.griddata.features.transmissionline.dto.UpdateTransmissionLineStateDto;
import com.intnet.griddata.features.transmissionline.service.TransmissionLineStateUpdaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/transmission-lines/state")
public class TransmissionLineStateUpdaterController {

    private final TransmissionLineStateUpdaterService stateUpdaterService;

    @Autowired
    public TransmissionLineStateUpdaterController(
            TransmissionLineStateUpdaterService stateUpdaterService
    ) {
        this.stateUpdaterService = stateUpdaterService;
    }

    @PutMapping
    public ResponseEntity<TransmissionLineStateDto> updateTransmissionLineState(@RequestBody UpdateTransmissionLineStateDto stateDto) {
        TransmissionLineStateDto updatedState = stateUpdaterService.updateTransmissionLineState(stateDto);
        return ResponseEntity.ok(updatedState);
    }
}
