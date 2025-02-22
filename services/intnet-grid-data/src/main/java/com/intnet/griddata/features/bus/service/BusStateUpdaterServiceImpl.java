package com.intnet.griddata.features.bus.service;

import com.intnet.griddata.features.bus.dto.BusMapper;
import com.intnet.griddata.features.bus.dto.BusStateDto;
import com.intnet.griddata.features.bus.dto.UpdateBusStateDto;
import com.intnet.griddata.features.bus.model.BusState;
import com.intnet.griddata.features.bus.repository.BusStateRepository;
import com.intnet.griddata.shared.exception.types.ResourceIdentifierType;
import com.intnet.griddata.shared.exception.types.ResourceNotFoundException;
import com.intnet.griddata.shared.exception.types.ResourceType;
import com.intnet.griddata.shared.sanitization.service.EntitySanitizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusStateUpdaterServiceImpl implements BusStateUpdaterService {

    private final BusStateRepository busStateRepository;
    private final EntitySanitizerService sanitizerService;

    @Autowired
    public BusStateUpdaterServiceImpl(
            BusStateRepository busStateRepository,
            EntitySanitizerService sanitizerService
    ) {
        this.busStateRepository = busStateRepository;
        this.sanitizerService = sanitizerService;
    }

    public BusStateDto updateBusState(UpdateBusStateDto stateDto) {
        UpdateBusStateDto sanitizedStateDto = sanitizerService.sanitizeUpdateBusStateDto(stateDto);

        BusState state = busStateRepository.findByBusId(stateDto.getBusId())
                .orElseThrow(() -> new ResourceNotFoundException(stateDto.getBusId().toString(), ResourceType.BUS_STATE, ResourceIdentifierType.BUS_ID));

        this.setUpdateBusStateDtoToBusState(state, sanitizedStateDto);

        BusState savedState = busStateRepository.save(state);

        return this.mapBusStateToBusStateDto(savedState);
    }

    private BusStateDto mapBusStateToBusStateDto(BusState state) {
        return BusMapper.INSTANCE.busStateToBusStateDto(state);
    }

    private void setUpdateBusStateDtoToBusState(BusState state, UpdateBusStateDto stateDto) {
        state.setVoltageMagnitude(stateDto.getVoltageMagnitude());
        state.setVoltageAngle(stateDto.getVoltageAngle());
        state.setActivePowerInjection(stateDto.getActivePowerInjection());
        state.setReactivePowerInjection(stateDto.getReactivePowerInjection());
        state.setShuntCapacitorReactorStatus(stateDto.getShuntCapacitorReactorStatus());
        state.setPhaseShiftingTransformerTapPosition(stateDto.getPhaseShiftingTransformerTapPosition());
    }
}
