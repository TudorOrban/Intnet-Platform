package com.intnet.griddata.features.load.service;

import com.intnet.griddata.features.load.dto.LoadMapper;
import com.intnet.griddata.features.load.dto.LoadStateDto;
import com.intnet.griddata.features.load.dto.UpdateLoadStateDto;
import com.intnet.griddata.features.load.model.LoadState;
import com.intnet.griddata.features.load.repository.LoadStateRepository;
import com.intnet.griddata.shared.exception.types.ResourceIdentifierType;
import com.intnet.griddata.shared.exception.types.ResourceNotFoundException;
import com.intnet.griddata.shared.exception.types.ResourceType;
import com.intnet.griddata.shared.sanitization.service.EntitySanitizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * Service responsible for updating the state of Loads
 */
@Service
public class LoadStateUpdaterServiceImpl implements LoadStateUpdaterService {

    private final LoadStateRepository stateRepository;
    private final EntitySanitizerService sanitizerService;

    @Autowired
    public LoadStateUpdaterServiceImpl(
            LoadStateRepository stateRepository,
            EntitySanitizerService sanitizerService
    ) {
        this.stateRepository = stateRepository;
        this.sanitizerService = sanitizerService;
    }

    public LoadStateDto updateLoadState(UpdateLoadStateDto stateDto) {
        UpdateLoadStateDto sanitizedDto = sanitizerService.sanitizeUpdateLoadStateDto(stateDto);

        LoadState state = stateRepository.findByLoadId(sanitizedDto.getLoadId())
                .orElseThrow(() -> new ResourceNotFoundException(sanitizedDto.getLoadId().toString(), ResourceType.GENERATOR, ResourceIdentifierType.GENERATOR_ID));

        this.setUpdateLoadStateDtoToLoadState(state, sanitizedDto);

        LoadState savedState = stateRepository.save(state);

        return this.mapLoadStateToLoadStateDto(savedState);
    }

    private LoadStateDto mapLoadStateToLoadStateDto(LoadState state) {
        return LoadMapper.INSTANCE.stateToStateSearchDto(state);
    }

    private void setUpdateLoadStateDtoToLoadState(LoadState state, UpdateLoadStateDto stateDto) {
        state.setActivePowerLoad(stateDto.getActivePowerLoad());
        state.setReactivePowerLoad(stateDto.getReactivePowerLoad());
    }
}
