package com.intnet.griddata.features.load.controller;

import com.intnet.griddata.features.load.dto.LoadStateDto;
import com.intnet.griddata.features.load.dto.UpdateLoadStateDto;
import com.intnet.griddata.features.load.service.LoadStateUpdaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/loads/state")
public class LoadStateUpdaterController {

    private final LoadStateUpdaterService stateUpdaterService;

    @Autowired
    public LoadStateUpdaterController(
            LoadStateUpdaterService stateUpdaterService
    ) {
        this.stateUpdaterService = stateUpdaterService;
    }

    @PutMapping
    public ResponseEntity<LoadStateDto> updateLoadState(@RequestBody UpdateLoadStateDto stateDto) {
        LoadStateDto updatedState = stateUpdaterService.updateLoadState(stateDto);
        return ResponseEntity.ok(updatedState);
    }
}
