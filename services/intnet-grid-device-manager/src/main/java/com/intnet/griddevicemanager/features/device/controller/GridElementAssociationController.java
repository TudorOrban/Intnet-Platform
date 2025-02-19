package com.intnet.griddevicemanager.features.device.controller;

import com.intnet.griddevicemanager.features.device.dto.AssociationSearchDto;
import com.intnet.griddevicemanager.features.device.dto.CreateAssociationDto;
import com.intnet.griddevicemanager.features.device.service.GridElementAssociationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/grid-element-associations")
public class GridElementAssociationController {

    private final GridElementAssociationService associationService;

    @Autowired
    public GridElementAssociationController(
            GridElementAssociationService associationService
    ) {
        this.associationService = associationService;
    }

    @GetMapping
    public ResponseEntity<List<AssociationSearchDto>> getAssociations() {
        List<AssociationSearchDto> associations = associationService.getAssociations();
        return ResponseEntity.ok(associations);
    }

    @PostMapping
    public ResponseEntity<AssociationSearchDto> createAssociation(@RequestBody CreateAssociationDto associationDto) {
        AssociationSearchDto savedAssociation = associationService.createAssociation(associationDto);
        return ResponseEntity.ok(savedAssociation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssociation(@PathVariable Long id) {
        associationService.deleteAssociation(id);
        return ResponseEntity.ok().build();
    }
}
