package com.intnet.gridtopology.features.graph.internal.in.griddata.controller;

import com.intnet.gridtopology.features.graph.internal.in.griddata.dto.CreateGridGraphDto;
import com.intnet.gridtopology.features.graph.model.GridGraph;
import com.intnet.gridtopology.features.graph.service.GridGraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/grid-graphs")
public class GridGraphController {

    private final GridGraphService graphService;

    @Autowired
    public GridGraphController(GridGraphService graphService) {
        this.graphService = graphService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<GridGraph> getGraphByGridId(@PathVariable Long gridId) {
        GridGraph graph = graphService.getGraphByGridId(gridId, true);
        return ResponseEntity.ok(graph);
    }

    @PostMapping
    public ResponseEntity<Void> createGraph(@RequestBody CreateGridGraphDto graphDto) {
        graphService.createGraph(graphDto);
        return ResponseEntity.ok().build();
    }

}
