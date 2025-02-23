package com.intnet.griddata.features.generator.controller;

import com.intnet.griddata.features.generator.dto.CreateGeneratorDto;
import com.intnet.griddata.features.generator.dto.GeneratorSearchDto;
import com.intnet.griddata.features.generator.dto.UpdateGeneratorDto;
import com.intnet.griddata.features.generator.service.GeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/generators")
public class GeneratorController {

    private final GeneratorService generatorService;

    @Autowired
    public GeneratorController(GeneratorService generatorService) {
        this.generatorService = generatorService;
    }

    @GetMapping("/grid/{gridId}")
    public ResponseEntity<List<GeneratorSearchDto>> getGeneratorsByGridId(@PathVariable Long gridId) {
        List<GeneratorSearchDto> generators = generatorService.getGeneratorsByGridId(gridId);
        return ResponseEntity.ok(generators);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GeneratorSearchDto> getGeneratorById(@PathVariable Long id) {
        GeneratorSearchDto generator = generatorService.getGeneratorById(id);
        return ResponseEntity.ok(generator);
    }

    @PostMapping
    public ResponseEntity<GeneratorSearchDto> createGenerator(@RequestBody CreateGeneratorDto generatorDto) {
        GeneratorSearchDto createdGenerator = generatorService.createGenerator(generatorDto);
        return ResponseEntity.ok(createdGenerator);
    }

    @PutMapping
    public ResponseEntity<GeneratorSearchDto> updateGenerator(@RequestBody UpdateGeneratorDto generatorDto) {
        GeneratorSearchDto updatedGenerator = generatorService.updateGenerator(generatorDto);
        return ResponseEntity.ok(updatedGenerator);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenerator(@PathVariable Long id) {
        generatorService.deleteGenerator(id);
        return ResponseEntity.ok().build();
    }

}
