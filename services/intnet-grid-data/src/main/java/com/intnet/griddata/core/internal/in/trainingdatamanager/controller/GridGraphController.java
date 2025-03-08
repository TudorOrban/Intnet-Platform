package com.intnet.griddata.core.internal.in.trainingdatamanager.controller;

import com.intnet.griddata.core.internal.in.trainingdatamanager.model.GridGraph;
import com.intnet.griddata.core.internal.in.trainingdatamanager.service.GridGraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/internal/grid-graphs")
public class GridGraphController {

    private final GridGraphService gridGraphService;

    @Autowired
    public GridGraphController(GridGraphService gridGraphService) {
        this.gridGraphService = gridGraphService;
    }
    @GetMapping("/{gridId}")
    private ResponseEntity<GridGraph> getGridGraph(@PathVariable Long gridId) {
        GridGraph graph = gridGraphService.getGridGraphByGridId(gridId);
        return ResponseEntity.ok(graph);
    }
}
