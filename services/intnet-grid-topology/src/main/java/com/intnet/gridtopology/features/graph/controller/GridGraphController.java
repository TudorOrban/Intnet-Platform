package com.intnet.gridtopology.features.graph.controller;

import com.intnet.gridtopology.features.graph.dto.CreateGridGraphDto;
import com.intnet.gridtopology.features.graph.model.GridGraph;
import com.intnet.gridtopology.features.graph.service.GridGraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/graphs")
public class GridGraphController {

    private final GridGraphService graphService;

    @Autowired
    public GridGraphController(GridGraphService graphService) {
        this.graphService = graphService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<GridGraph> getGraphById(@PathVariable Long id) {
        GridGraph graph = graphService.loadGraphFromNeo4j(id, true);
        return ResponseEntity.ok(graph);
    }

    @PostMapping
    public ResponseEntity<Void> createGraph(@RequestBody CreateGridGraphDto graphDto) {
        graphService.createGraph(graphDto);
        return ResponseEntity.ok().build();
    }
}
