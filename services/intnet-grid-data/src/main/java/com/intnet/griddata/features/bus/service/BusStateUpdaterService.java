package com.intnet.griddata.features.bus.service;

import com.intnet.griddata.features.bus.dto.BusStateDto;
import com.intnet.griddata.features.bus.dto.UpdateBusStateDto;

public interface BusStateUpdaterService {

    BusStateDto updateBusState(UpdateBusStateDto stateDto);
}
