package com.intnet.griddata.features.edge.controller;

import com.intnet.griddata.features.edge.dto.EdgeSearchDto;
import com.intnet.griddata.features.edge.dto.CreateEdgeDto;
import com.intnet.griddata.features.edge.dto.UpdateEdgeDto;
import com.intnet.griddata.features.edge.service.EdgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/edges")
public class EdgeController {

    private final EdgeService edgeService;

    @Autowired
    public EdgeController(EdgeService edgeService) {
        this.edgeService = edgeService;
    }

    @GetMapping("/grid/{gridId}")
    public ResponseEntity<List<EdgeSearchDto>> getEdgesByGridId(
            @PathVariable Long gridId,
            @RequestParam(name = "attachComponents", required = false, defaultValue = "false") Boolean attachComponents
    ) {
        List<EdgeSearchDto> edges = edgeService.getEdgesByGridId(gridId, attachComponents);
        return ResponseEntity.ok(edges);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EdgeSearchDto> getEdgeById(@PathVariable Long id) {
        EdgeSearchDto edge = edgeService.getEdgeById(id);
        return ResponseEntity.ok(edge);
    }

    @PostMapping
    public ResponseEntity<EdgeSearchDto> createEdge(@RequestBody CreateEdgeDto edgeDto) {
        EdgeSearchDto savedEdge = edgeService.createEdge(edgeDto);
        return ResponseEntity.ok(savedEdge);
    }

    @PutMapping
    public ResponseEntity<EdgeSearchDto> updateEdge(@RequestBody UpdateEdgeDto edgeDto) {
        EdgeSearchDto updatedEdge = edgeService.updateEdge(edgeDto);
        return ResponseEntity.ok(updatedEdge);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEdge(@PathVariable Long id) {
        edgeService.deleteEdge(id);
        return ResponseEntity.ok().build();
    }
}
