package com.intnet.griddata.features.edge.service;

import com.intnet.griddata.features.edge.dto.EdgeMapper;
import com.intnet.griddata.features.edge.dto.EdgeStateDto;
import com.intnet.griddata.features.edge.dto.UpdateEdgeStateDto;
import com.intnet.griddata.features.edge.model.EdgeState;
import com.intnet.griddata.features.edge.repository.EdgeStateRepository;
import com.intnet.griddata.shared.exception.types.ResourceIdentifierType;
import com.intnet.griddata.shared.exception.types.ResourceNotFoundException;
import com.intnet.griddata.shared.exception.types.ResourceType;
import com.intnet.griddata.shared.sanitization.service.EntitySanitizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * Service responsible for updating the state of the Edge feature
 */
@Service
public class EdgeStateUpdaterServiceImpl implements EdgeStateUpdaterService {

    private final EdgeStateRepository stateRepository;
    private final EntitySanitizerService sanitizerService;

    @Autowired
    public EdgeStateUpdaterServiceImpl(
            EdgeStateRepository stateRepository,
            EntitySanitizerService sanitizerService
    ) {
        this.stateRepository = stateRepository;
        this.sanitizerService = sanitizerService;
    }

    public EdgeStateDto updateEdgeState(UpdateEdgeStateDto stateDto) {
        UpdateEdgeStateDto sanitizedStateDto = sanitizerService.sanitizeUpdateEdgeStateDto(stateDto);

        EdgeState state = stateRepository.findByEdgeId(stateDto.getEdgeId())
                .orElseThrow(() -> new ResourceNotFoundException(stateDto.getEdgeId().toString(), ResourceType.BUS_STATE, ResourceIdentifierType.BUS_ID));

        this.setUpdateEdgeStateDtoToEdgeState(state, sanitizedStateDto);

        EdgeState savedState = stateRepository.save(state);

        return this.mapEdgeStateToEdgeStateDto(savedState);
    }

    private EdgeStateDto mapEdgeStateToEdgeStateDto(EdgeState state) {
        return EdgeMapper.INSTANCE.edgeStateToEdgeStateDto(state);
    }

    private void setUpdateEdgeStateDtoToEdgeState(EdgeState state, UpdateEdgeStateDto stateDto) {
        state.setLineSwitchingStatus(stateDto.getLineSwitchingStatus());
    }
}
