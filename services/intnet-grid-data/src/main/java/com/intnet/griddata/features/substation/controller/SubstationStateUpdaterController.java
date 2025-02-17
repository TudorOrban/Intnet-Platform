package com.intnet.griddata.features.substation.controller;

import com.intnet.griddata.features.substation.dto.SubstationStateDto;
import com.intnet.griddata.features.substation.dto.UpdateSubstationStateDto;
import com.intnet.griddata.features.substation.service.SubstationStateUpdaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/substations/state")
public class SubstationStateUpdaterController {

    private final SubstationStateUpdaterService stateUpdaterService;

    @Autowired
    public SubstationStateUpdaterController(
            SubstationStateUpdaterService stateUpdaterService
    ) {
        this.stateUpdaterService = stateUpdaterService;
    }

    @PutMapping
    public ResponseEntity<SubstationStateDto> updateSubstationState(@RequestBody UpdateSubstationStateDto stateDto) {
        SubstationStateDto updatedState = stateUpdaterService.updateSubstationState(stateDto);
        return ResponseEntity.ok(updatedState);
    }
}
