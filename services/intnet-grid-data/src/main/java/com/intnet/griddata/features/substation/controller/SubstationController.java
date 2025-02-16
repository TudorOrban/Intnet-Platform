package com.intnet.griddata.features.substation.controller;

import com.intnet.griddata.features.substation.dto.CreateSubstationDto;
import com.intnet.griddata.features.substation.dto.SubstationSearchDto;
import com.intnet.griddata.features.substation.dto.UpdateSubstationDto;
import com.intnet.griddata.features.substation.service.SubstationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/substations")
public class SubstationController {

    private final SubstationService substationService;

    @Autowired
    public SubstationController(SubstationService substationService) {
        this.substationService = substationService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubstationSearchDto> getSubstationById(@PathVariable Long id) {
        SubstationSearchDto substation = substationService.getSubstationById(id);
        return ResponseEntity.ok(substation);
    }

    @PostMapping
    public ResponseEntity<SubstationSearchDto> createSubstation(@RequestBody CreateSubstationDto substationDto) {
        SubstationSearchDto savedSubstation = substationService.createSubstation(substationDto);
        return ResponseEntity.ok(savedSubstation);
    }

    @PutMapping
    public ResponseEntity<SubstationSearchDto> updateSubstation(@RequestBody UpdateSubstationDto substationDto) {
        SubstationSearchDto updatedSubstation = substationService.updateSubstation(substationDto);
        return ResponseEntity.ok(updatedSubstation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubstation(@PathVariable Long id) {
        substationService.deleteSubstation(id);
        return ResponseEntity.ok().build();
    }
}
