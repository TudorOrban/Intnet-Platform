package com.intnet.griddata.core.grid.controller;

import com.intnet.griddata.core.grid.dto.CreateGridDto;
import com.intnet.griddata.core.grid.dto.GridSearchDto;
import com.intnet.griddata.core.grid.dto.UpdateGridDto;
import com.intnet.griddata.core.grid.service.GridService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/grids")
public class GridController {

    private final GridService gridService;

    @Autowired
    public GridController(GridService gridService) {
        this.gridService = gridService;
    }

    @GetMapping
    public ResponseEntity<List<GridSearchDto>> getAllGrids() {
        List<GridSearchDto> grids = gridService.getGrids();
        return ResponseEntity.ok(grids);
    }

    @PostMapping
    public ResponseEntity<GridSearchDto> createGrid(@RequestBody CreateGridDto gridDto) {
        GridSearchDto createdGrid = gridService.createGrid(gridDto);
        return ResponseEntity.ok(createdGrid);
    }

    @PutMapping
    public ResponseEntity<GridSearchDto> updateGrid(@RequestBody UpdateGridDto gridDto) {
        GridSearchDto updatedGrid = gridService.updateGrid(gridDto);
        return ResponseEntity.ok(updatedGrid);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrid(@PathVariable Long id) {
        gridService.deleteGrid(id);
        return ResponseEntity.ok().build();
    }
}
