package com.intnet.griddata.features.generator.controller;

import com.intnet.griddata.features.generator.dto.GeneratorStateDto;
import com.intnet.griddata.features.generator.dto.UpdateGeneratorStateDto;
import com.intnet.griddata.features.generator.service.GeneratorStateUpdaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/generators/state")
public class GeneratorStateUpdaterController {

    private final GeneratorStateUpdaterService stateUpdaterService;

    @Autowired
    public GeneratorStateUpdaterController(
            GeneratorStateUpdaterService stateUpdaterService
    ) {
        this.stateUpdaterService = stateUpdaterService;
    }

    @PutMapping
    public ResponseEntity<GeneratorStateDto> updateGeneratorState(@RequestBody UpdateGeneratorStateDto stateDto) {
        GeneratorStateDto updatedState = stateUpdaterService.updateGeneratorState(stateDto);
        return ResponseEntity.ok(updatedState);
    }
}
