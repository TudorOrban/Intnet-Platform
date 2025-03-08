package com.intnet.griddata.core.internal.in.trainingdatamanager.controller;

import com.intnet.griddata.core.internal.in.trainingdatamanager.model.GridGraph;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/internal/grid-graphs")
public class GridGraphController {

    @GetMapping("/{gridId}")
    private ResponseEntity<GridGraph> getGridGraph(@PathVariable Integer gridId) {
        GridGraph graph = new GridGraph(); // TODO: Implement
        return ResponseEntity.ok(graph);
    }
}
