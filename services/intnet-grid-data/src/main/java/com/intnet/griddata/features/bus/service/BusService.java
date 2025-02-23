package com.intnet.griddata.features.bus.service;

import com.intnet.griddata.features.bus.dto.CreateBusDto;
import com.intnet.griddata.features.bus.dto.BusSearchDto;
import com.intnet.griddata.features.bus.dto.UpdateBusDto;

import java.util.List;

public interface BusService {

    List<BusSearchDto> getBusesByGridId(Long gridId, Boolean attachComponents);
    BusSearchDto getBusById(Long id);
    BusSearchDto createBus(CreateBusDto busDto);
    BusSearchDto updateBus(UpdateBusDto busDto);
    void deleteBus(Long id);
}
