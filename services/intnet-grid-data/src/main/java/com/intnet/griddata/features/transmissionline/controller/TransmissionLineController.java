package com.intnet.griddata.features.transmissionline.controller;

import com.intnet.griddata.features.transmissionline.dto.TransmissionLineSearchDto;
import com.intnet.griddata.features.transmissionline.dto.CreateTransmissionLineDto;
import com.intnet.griddata.features.transmissionline.dto.UpdateTransmissionLineDto;
import com.intnet.griddata.features.transmissionline.service.TransmissionLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/transmission-lines")
public class TransmissionLineController {

    private final TransmissionLineService transmissionLineService;

    @Autowired
    public TransmissionLineController(TransmissionLineService transmissionLineService) {
        this.transmissionLineService = transmissionLineService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransmissionLineSearchDto> getTransmissionLineById(
            @PathVariable Long id,
            @RequestParam(name = "attachState", required = false, defaultValue = "false") Boolean attachState
    ) {
        TransmissionLineSearchDto transmissionLine = transmissionLineService.getTransmissionLineById(id, attachState);
        return ResponseEntity.ok(transmissionLine);
    }

    @PostMapping
    public ResponseEntity<TransmissionLineSearchDto> createTransmissionLine(@RequestBody CreateTransmissionLineDto transmissionLineDto) {
        TransmissionLineSearchDto savedTransmissionLine = transmissionLineService.createTransmissionLine(transmissionLineDto);
        return ResponseEntity.ok(savedTransmissionLine);
    }

    @PutMapping
    public ResponseEntity<TransmissionLineSearchDto> updateTransmissionLine(@RequestBody UpdateTransmissionLineDto transmissionLineDto) {
        TransmissionLineSearchDto updatedTransmissionLine = transmissionLineService.updateTransmissionLine(transmissionLineDto);
        return ResponseEntity.ok(updatedTransmissionLine);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransmissionLine(@PathVariable Long id) {
        transmissionLineService.deleteTransmissionLine(id);
        return ResponseEntity.ok().build();
    }
}
