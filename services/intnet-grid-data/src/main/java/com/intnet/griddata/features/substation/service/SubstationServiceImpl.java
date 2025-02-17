package com.intnet.griddata.features.substation.service;

import com.intnet.griddata.core.internal.out.gridtopology.service.GridGraphUpdaterService;
import com.intnet.griddata.features.substation.dto.*;
import com.intnet.griddata.features.substation.model.Substation;
import com.intnet.griddata.features.substation.model.SubstationState;
import com.intnet.griddata.features.substation.repository.SubstationRepository;
import com.intnet.griddata.features.substation.repository.SubstationStateRepository;
import com.intnet.griddata.shared.exception.types.ResourceIdentifierType;
import com.intnet.griddata.shared.exception.types.ResourceNotFoundException;
import com.intnet.griddata.shared.exception.types.ResourceType;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubstationServiceImpl implements SubstationService {

    private final SubstationRepository substationRepository;
    private final SubstationStateRepository substationStateRepository;
    private final GridGraphUpdaterService graphUpdaterService;

    @Autowired
    public SubstationServiceImpl(
            SubstationRepository substationRepository,
            SubstationStateRepository substationStateRepository,
            GridGraphUpdaterService graphUpdaterService
    ) {
        this.substationRepository = substationRepository;
        this.substationStateRepository = substationStateRepository;
        this.graphUpdaterService = graphUpdaterService;
    }

    public SubstationSearchDto getSubstationById(Long id, Boolean attachState) {
        Substation substation = substationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id.toString(), ResourceType.SUBSTATION, ResourceIdentifierType.ID));

        SubstationStateDto stateDto = null;
        if (attachState) {
            SubstationState state = substationStateRepository.findBySubstationId(substation.getId())
                    .orElseThrow(() -> new ResourceNotFoundException(substation.getId().toString(), ResourceType.SUBSTATION_STATE, ResourceIdentifierType.SUBSTATION_ID));
            stateDto = this.mapSubstationStateToSubstationStateDto(state);
        }

        return this.mapSubstationToSubstationSearchDto(substation, stateDto);
    }

    @Transactional
    public SubstationSearchDto createSubstation(CreateSubstationDto substationDto) {
        Substation substation = this.mapCreateSubstationDtoToSubstation(substationDto);

        Substation savedSubstation = substationRepository.save(substation);

        SubstationStateDto stateDto = this.createSubstationState(savedSubstation);

        graphUpdaterService.updateGraph(savedSubstation);

        return this.mapSubstationToSubstationSearchDto(savedSubstation, stateDto);
    }

    private SubstationStateDto createSubstationState(Substation savedSubstation) {
        SubstationState substationState = new SubstationState();
        substationState.setGridId(savedSubstation.getGridId());
        substationState.setSubstationId(savedSubstation.getId());

        SubstationState savedState = substationStateRepository.save(substationState);
        return this.mapSubstationStateToSubstationStateDto(savedState);
    }

    public SubstationSearchDto updateSubstation(UpdateSubstationDto substationDto) {
        Substation substation = substationRepository.findById(substationDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException(substationDto.getId().toString(), ResourceType.SUBSTATION, ResourceIdentifierType.ID));

        substation.setLatitude(substationDto.getLatitude());
        substation.setLongitude(substationDto.getLongitude());
        substation.setTransformers(substationDto.getTransformers());
        substation.setConnectedBuses(substationDto.getConnectedBuses());

        Substation savedSubstation = substationRepository.save(substation);

        return this.mapSubstationToSubstationSearchDto(savedSubstation, null);
    }

    public void deleteSubstation(Long id) {
        Substation substation = substationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id.toString(), ResourceType.SUBSTATION, ResourceIdentifierType.ID));

        this.substationRepository.delete(substation);
    }

    private SubstationSearchDto mapSubstationToSubstationSearchDto(Substation substation, SubstationStateDto stateDto) {
        SubstationSearchDto substationDto = SubstationMapper.INSTANCE.substationToSubstationSearchDto(substation);
        substationDto.setState(stateDto);
        return substationDto;
    }

    private Substation mapCreateSubstationDtoToSubstation(CreateSubstationDto substationDto) {
        return SubstationMapper.INSTANCE.createSubstationDtoToSubstation(substationDto);
    }

    private SubstationStateDto mapSubstationStateToSubstationStateDto(SubstationState state) {
        return SubstationMapper.INSTANCE.substationStateToSubstationStateDto(state);
    }
}
