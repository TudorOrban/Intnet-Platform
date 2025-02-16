package com.intnet.griddata.features.substation.service;

import com.intnet.griddata.features.substation.dto.CreateSubstationDto;
import com.intnet.griddata.features.substation.dto.SubstationMapper;
import com.intnet.griddata.features.substation.dto.SubstationSearchDto;
import com.intnet.griddata.features.substation.dto.UpdateSubstationDto;
import com.intnet.griddata.features.substation.model.Substation;
import com.intnet.griddata.features.substation.repository.SubstationRepository;
import com.intnet.griddata.shared.exception.types.ResourceIdentifierType;
import com.intnet.griddata.shared.exception.types.ResourceNotFoundException;
import com.intnet.griddata.shared.exception.types.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubstationServiceImpl implements SubstationService {

    private final SubstationRepository substationRepository;

    @Autowired
    public SubstationServiceImpl(SubstationRepository substationRepository) {
        this.substationRepository = substationRepository;
    }

    public SubstationSearchDto getSubstationById(Long id) {
        Substation substation = substationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id.toString(), ResourceType.SUBSTATION, ResourceIdentifierType.ID));

        return this.mapSubstationToSubstationSearchDto(substation);
    }

    public SubstationSearchDto createSubstation(CreateSubstationDto substationDto) {
        Substation substation = this.mapCreateSubstationDtoToSubstation(substationDto);

        Substation savedSubstation = substationRepository.save(substation);

        return this.mapSubstationToSubstationSearchDto(savedSubstation);
    }

    public SubstationSearchDto updateSubstation(UpdateSubstationDto substationDto) {
        Substation substation = substationRepository.findById(substationDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException(substationDto.getId().toString(), ResourceType.SUBSTATION, ResourceIdentifierType.ID));

        substation.setLatitude(substationDto.getLatitude());
        substation.setLongitude(substationDto.getLongitude());
        substation.setTransformers(substationDto.getTransformers());
        substation.setConnectedBuses(substationDto.getConnectedBuses());

        Substation savedSubstation = substationRepository.save(substation);

        return this.mapSubstationToSubstationSearchDto(savedSubstation);
    }

    public void deleteSubstation(Long id) {
        Substation substation = substationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id.toString(), ResourceType.SUBSTATION, ResourceIdentifierType.ID));

        this.substationRepository.delete(substation);
    }

    private SubstationSearchDto mapSubstationToSubstationSearchDto(Substation substation) {
        return SubstationMapper.INSTANCE.substationToSubstationSearchDto(substation);
    }

    private Substation mapCreateSubstationDtoToSubstation(CreateSubstationDto substationDto) {
        return SubstationMapper.INSTANCE.createSubstationDtoToSubstation(substationDto);
    }
}
