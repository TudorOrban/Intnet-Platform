package com.intnet.griddata.features.substation.service;

import com.intnet.griddata.features.substation.dto.SubstationMapper;
import com.intnet.griddata.features.substation.dto.SubstationStateDto;
import com.intnet.griddata.features.substation.dto.UpdateSubstationStateDto;
import com.intnet.griddata.features.substation.model.SubstationState;
import com.intnet.griddata.features.substation.repository.SubstationStateRepository;
import com.intnet.griddata.shared.exception.types.ResourceIdentifierType;
import com.intnet.griddata.shared.exception.types.ResourceNotFoundException;
import com.intnet.griddata.shared.exception.types.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubstationStateUpdaterServiceImpl implements SubstationStateUpdaterService {

    private final SubstationStateRepository substationStateRepository;

    @Autowired
    public SubstationStateUpdaterServiceImpl(
            SubstationStateRepository substationStateRepository
    ) {
        this.substationStateRepository = substationStateRepository;
    }

    public SubstationStateDto updateSubstationState(UpdateSubstationStateDto stateDto) {
        SubstationState state = substationStateRepository.findBySubstationId(stateDto.getSubstationId())
                .orElseThrow(() -> new ResourceNotFoundException(stateDto.getSubstationId().toString(), ResourceType.SUBSTATION_STATE, ResourceIdentifierType.SUBSTATION_ID));

        state.setVoltage(stateDto.getVoltage());
        state.setFrequency(stateDto.getFrequency());
        state.setTemperature(stateDto.getTemperature());
        state.setLoad(stateDto.getLoad());
        state.setTotalInflow(stateDto.getTotalInflow());
        state.setTotalOutflow(stateDto.getTotalOutflow());

        SubstationState savedState = substationStateRepository.save(state);

        return this.mapSubstationStateToSubstationStateDto(savedState);
    }

    private SubstationStateDto mapSubstationStateToSubstationStateDto(SubstationState state) {
        return SubstationMapper.INSTANCE.substationStateToSubstationStateDto(state);
    }
}
