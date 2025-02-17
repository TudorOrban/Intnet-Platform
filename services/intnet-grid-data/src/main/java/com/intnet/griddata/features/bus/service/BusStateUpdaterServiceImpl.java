package com.intnet.griddata.features.bus.service;

import com.intnet.griddata.features.bus.dto.BusMapper;
import com.intnet.griddata.features.bus.dto.BusStateDto;
import com.intnet.griddata.features.bus.dto.UpdateBusStateDto;
import com.intnet.griddata.features.bus.model.BusState;
import com.intnet.griddata.features.bus.repository.BusStateRepository;
import com.intnet.griddata.shared.exception.types.ResourceIdentifierType;
import com.intnet.griddata.shared.exception.types.ResourceNotFoundException;
import com.intnet.griddata.shared.exception.types.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusStateUpdaterServiceImpl implements BusStateUpdaterService {

    private final BusStateRepository busStateRepository;

    @Autowired
    public BusStateUpdaterServiceImpl(
            BusStateRepository busStateRepository
    ) {
        this.busStateRepository = busStateRepository;
    }

    public BusStateDto updateBusState(UpdateBusStateDto stateDto) {
        BusState state = busStateRepository.findByBusId(stateDto.getBusId())
                .orElseThrow(() -> new ResourceNotFoundException(stateDto.getBusId().toString(), ResourceType.BUS_STATE, ResourceIdentifierType.BUS_ID));

        state.setVoltage(stateDto.getVoltage());
        state.setLoad(stateDto.getLoad());
        state.setGeneration(stateDto.getGeneration());
        state.setPhaseAngle(stateDto.getPhaseAngle());

        BusState savedState = busStateRepository.save(state);

        return this.mapBusStateToBusStateDto(savedState);
    }

    private BusStateDto mapBusStateToBusStateDto(BusState state) {
        return BusMapper.INSTANCE.busStateToBusStateDto(state);
    }
}
