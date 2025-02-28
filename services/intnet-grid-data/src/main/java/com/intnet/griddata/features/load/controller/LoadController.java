package com.intnet.griddata.features.load.controller;

import com.intnet.griddata.features.load.dto.CreateLoadDto;
import com.intnet.griddata.features.load.dto.LoadSearchDto;
import com.intnet.griddata.features.load.dto.UpdateLoadDto;
import com.intnet.griddata.features.load.service.LoadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/loads")
public class LoadController {

    private final LoadService loadService;

    @Autowired
    public LoadController(LoadService loadService) {
        this.loadService = loadService;
    }

    @GetMapping("/grid/{gridId}")
    public ResponseEntity<List<LoadSearchDto>> getLoadsByGridId(@PathVariable Long gridId) {
        List<LoadSearchDto> loads = loadService.getLoadsByGridId(gridId);
        return ResponseEntity.ok(loads);
    }

    @GetMapping("/bus/{busId}")
    public ResponseEntity<List<LoadSearchDto>> getLoadsByBusId(@PathVariable Long busId) {
        List<LoadSearchDto> loads = loadService.getLoadsByBusId(busId);
        return ResponseEntity.ok(loads);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoadSearchDto> getLoadById(@PathVariable Long id) {
        LoadSearchDto load = loadService.getLoadById(id);
        return ResponseEntity.ok(load);
    }

    @PostMapping
    public ResponseEntity<LoadSearchDto> createLoad(@RequestBody CreateLoadDto loadDto) {
        LoadSearchDto createdLoad = loadService.createLoad(loadDto);
        return ResponseEntity.ok(createdLoad);
    }

    @PutMapping
    public ResponseEntity<LoadSearchDto> updateLoad(@RequestBody UpdateLoadDto loadDto) {
        LoadSearchDto updatedLoad = loadService.updateLoad(loadDto);
        return ResponseEntity.ok(updatedLoad);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoad(@PathVariable Long id) {
        loadService.deleteLoad(id);
        return ResponseEntity.ok().build();
    }

}
