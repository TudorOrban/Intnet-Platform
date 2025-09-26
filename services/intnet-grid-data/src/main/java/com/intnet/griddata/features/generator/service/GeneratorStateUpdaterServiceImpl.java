package com.intnet.griddata.features.generator.service;

import com.intnet.griddata.features.generator.dto.GeneratorMapper;
import com.intnet.griddata.features.generator.dto.GeneratorStateDto;
import com.intnet.griddata.features.generator.dto.UpdateGeneratorStateDto;
import com.intnet.griddata.features.generator.model.GeneratorState;
import com.intnet.griddata.features.generator.repository.GeneratorStateRepository;
import com.intnet.griddata.shared.exception.types.ResourceIdentifierType;
import com.intnet.griddata.shared.exception.types.ResourceNotFoundException;
import com.intnet.griddata.shared.exception.types.ResourceType;
import com.intnet.griddata.shared.sanitization.service.EntitySanitizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * Service responsible for updating the state of the Generators
 */
@Service
public class GeneratorStateUpdaterServiceImpl implements GeneratorStateUpdaterService {

    private final GeneratorStateRepository stateRepository;
    private final EntitySanitizerService sanitizerService;

    @Autowired
    public GeneratorStateUpdaterServiceImpl(
            GeneratorStateRepository stateRepository,
            EntitySanitizerService sanitizerService
    ) {
        this.stateRepository = stateRepository;
        this.sanitizerService = sanitizerService;
    }

    public GeneratorStateDto updateGeneratorState(UpdateGeneratorStateDto stateDto) {
        UpdateGeneratorStateDto sanitizedDto = sanitizerService.sanitizeUpdateGeneratorStateDto(stateDto);

        GeneratorState state = stateRepository.findByGeneratorId(sanitizedDto.getGeneratorId())
                .orElseThrow(() -> new ResourceNotFoundException(sanitizedDto.getGeneratorId().toString(), ResourceType.GENERATOR, ResourceIdentifierType.GENERATOR_ID));

        this.setUpdateGeneratorStateDtoToGeneratorState(state, sanitizedDto);

        GeneratorState savedState = stateRepository.save(state);

        return this.mapGeneratorStateToGeneratorStateDto(savedState);
    }

    private GeneratorStateDto mapGeneratorStateToGeneratorStateDto(GeneratorState state) {
        return GeneratorMapper.INSTANCE.stateToStateSearchDto(state);
    }

    private void setUpdateGeneratorStateDtoToGeneratorState(GeneratorState state, UpdateGeneratorStateDto stateDto) {
        state.setActivePowerGeneration(stateDto.getActivePowerGeneration());
        state.setReactivePowerGeneration(stateDto.getReactivePowerGeneration());
        state.setGeneratorVoltageSetpoint(stateDto.getGeneratorVoltageSetpoint());
    }
}
