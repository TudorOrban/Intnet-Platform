package com.intnet.griddata.features.substation.service;

import com.intnet.griddata.features.substation.dto.SubstationMapper;
import com.intnet.griddata.features.substation.dto.SubstationStateDto;
import com.intnet.griddata.features.substation.dto.UpdateSubstationStateDto;
import com.intnet.griddata.features.substation.model.SubstationState;
import com.intnet.griddata.features.substation.repository.SubstationStateRepository;
import com.intnet.griddata.shared.exception.types.ResourceIdentifierType;
import com.intnet.griddata.shared.exception.types.ResourceNotFoundException;
import com.intnet.griddata.shared.exception.types.ResourceType;
import com.intnet.griddata.shared.sanitization.service.EntitySanitizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubstationStateUpdaterServiceImpl implements SubstationStateUpdaterService {

    private final SubstationStateRepository substationStateRepository;
    private final EntitySanitizerService sanitizerService;

    @Autowired
    public SubstationStateUpdaterServiceImpl(
            SubstationStateRepository substationStateRepository,
            EntitySanitizerService sanitizerService
    ) {
        this.substationStateRepository = substationStateRepository;
        this.sanitizerService = sanitizerService;
    }

    public SubstationStateDto updateSubstationState(UpdateSubstationStateDto stateDto) {
        UpdateSubstationStateDto sanitizedStateDto = sanitizerService.sanitizeUpdateSubstationStateDto(stateDto);

        SubstationState state = substationStateRepository.findBySubstationId(sanitizedStateDto.getSubstationId())
                .orElseThrow(() -> new ResourceNotFoundException(sanitizedStateDto.getSubstationId().toString(), ResourceType.SUBSTATION_STATE, ResourceIdentifierType.SUBSTATION_ID));

        this.setUpdateSubstationStateDtoToSubstationState(state, sanitizedStateDto);

        SubstationState savedState = substationStateRepository.save(state);

        return this.mapSubstationStateToSubstationStateDto(savedState);
    }

    private SubstationStateDto mapSubstationStateToSubstationStateDto(SubstationState state) {
        return SubstationMapper.INSTANCE.substationStateToSubstationStateDto(state);
    }

    private void setUpdateSubstationStateDtoToSubstationState(SubstationState state, UpdateSubstationStateDto stateDto) {
        state.setVoltage(stateDto.getVoltage());
        state.setFrequency(stateDto.getFrequency());
        state.setTemperature(stateDto.getTemperature());
        state.setLoad(stateDto.getLoad());
        state.setTotalInflow(stateDto.getTotalInflow());
        state.setTotalOutflow(stateDto.getTotalOutflow());
    }
}
