package com.intnet.griddata.features.bus.controller;

import com.intnet.griddata.features.bus.dto.CreateBusDto;
import com.intnet.griddata.features.bus.dto.BusSearchDto;
import com.intnet.griddata.features.bus.dto.UpdateBusDto;
import com.intnet.griddata.features.bus.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/buses")
public class BusController {

    private final BusService busService;

    @Autowired
    public BusController(BusService busService) {
        this.busService = busService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BusSearchDto> getBusById(
            @PathVariable Long id,
            @RequestParam(name = "attachUsers", required = false, defaultValue = "false") Boolean attachUsers
    ) {
        BusSearchDto bus = busService.getBusById(id, attachUsers);
        return ResponseEntity.ok(bus);
    }

    @PostMapping
    public ResponseEntity<BusSearchDto> createBus(@RequestBody CreateBusDto busDto) {
        BusSearchDto savedBus = busService.createBus(busDto);
        return ResponseEntity.ok(savedBus);
    }

    @PutMapping
    public ResponseEntity<BusSearchDto> updateBus(@RequestBody UpdateBusDto busDto) {
        BusSearchDto updatedBus = busService.updateBus(busDto);
        return ResponseEntity.ok(updatedBus);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBus(@PathVariable Long id) {
        busService.deleteBus(id);
        return ResponseEntity.ok().build();
    }
}
