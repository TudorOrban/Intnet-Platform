package com.intnet.griddevicemanager.features.device.service;

import com.intnet.griddevicemanager.features.device.dto.AssociationSearchDto;
import com.intnet.griddevicemanager.features.device.dto.CreateAssociationDto;

import java.util.List;

public interface GridElementAssociationService {

    List<AssociationSearchDto> getAssociations();
    AssociationSearchDto createAssociation(CreateAssociationDto associationDto);
    void deleteAssociation(Long id);
}
