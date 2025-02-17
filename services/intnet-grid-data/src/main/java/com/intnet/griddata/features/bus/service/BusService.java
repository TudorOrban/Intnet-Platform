package com.intnet.griddata.features.bus.service;

import com.intnet.griddata.features.bus.dto.CreateBusDto;
import com.intnet.griddata.features.bus.dto.BusSearchDto;
import com.intnet.griddata.features.bus.dto.UpdateBusDto;

public interface BusService {

    BusSearchDto getBusById(Long id, Boolean attachState);
    BusSearchDto createBus(CreateBusDto busDto);
    BusSearchDto updateBus(UpdateBusDto busDto);
    void deleteBus(Long id);
}
