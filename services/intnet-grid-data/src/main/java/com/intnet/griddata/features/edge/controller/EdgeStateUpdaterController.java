package com.intnet.griddata.features.edge.controller;

import com.intnet.griddata.features.edge.dto.EdgeStateDto;
import com.intnet.griddata.features.edge.dto.UpdateEdgeStateDto;
import com.intnet.griddata.features.edge.service.EdgeStateUpdaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/edges/state")
public class EdgeStateUpdaterController {

    private final EdgeStateUpdaterService stateUpdaterService;

    @Autowired
    public EdgeStateUpdaterController(
            EdgeStateUpdaterService stateUpdaterService
    ) {
        this.stateUpdaterService = stateUpdaterService;
    }

    @PutMapping
    public ResponseEntity<EdgeStateDto> updateEdgeState(@RequestBody UpdateEdgeStateDto stateDto) {
        EdgeStateDto updatedState = stateUpdaterService.updateEdgeState(stateDto);
        return ResponseEntity.ok(updatedState);
    }
}
