package com.intnet.griddevicemanager.features.device.service;

import com.intnet.griddevicemanager.features.device.dto.AssociationSearchDto;
import com.intnet.griddevicemanager.features.device.dto.CreateAssociationDto;
import com.intnet.griddevicemanager.features.device.dto.DeviceMapper;
import com.intnet.griddevicemanager.features.device.model.GridElementAssociation;
import com.intnet.griddevicemanager.features.device.repository.DeviceRepository;
import com.intnet.griddevicemanager.features.device.repository.GridElementAssociationRepository;
import com.intnet.griddevicemanager.shared.exception.types.ResourceIdentifierType;
import com.intnet.griddevicemanager.shared.exception.types.ResourceNotFoundException;
import com.intnet.griddevicemanager.shared.exception.types.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * Service responsible with CRUD ops for GridAssociation entity
 */
@Service
public class GridElementAssociationServiceImpl implements GridElementAssociationService {

    private final GridElementAssociationRepository associationRepository;
    private final DeviceRepository deviceRepository;

    @Autowired
    public GridElementAssociationServiceImpl(
            GridElementAssociationRepository associationRepository,
            DeviceRepository deviceRepository
    ) {
        this.associationRepository = associationRepository;
        this.deviceRepository = deviceRepository;
    }

    public List<AssociationSearchDto> getAssociations() {
        List<GridElementAssociation> associations = associationRepository.findAll();

        return associations.stream().map(this::mapAssociationToAssociationSearchDto).toList();
    }

    public AssociationSearchDto createAssociation(CreateAssociationDto associationDto) {
        if (!deviceRepository.existsById(associationDto.getDeviceId())) {
            throw new ResourceNotFoundException(associationDto.getDeviceId().toString(), ResourceType.DEVICE, ResourceIdentifierType.ID);
        }

        GridElementAssociation association = this.mapCreateAssociationDtoToAssociation(associationDto);

        GridElementAssociation savedAssociation = associationRepository.save(association);

        return this.mapAssociationToAssociationSearchDto(savedAssociation);
    }

    public void deleteAssociation(Long id) {
        GridElementAssociation existingAssociation = associationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id.toString(), ResourceType.ASSOCIATION, ResourceIdentifierType.ID));

        associationRepository.delete(existingAssociation);
    }

    private AssociationSearchDto mapAssociationToAssociationSearchDto(GridElementAssociation association) {
        return DeviceMapper.INSTANCE.mapAssociationToAssociationSearchDto(association);
    }

    private GridElementAssociation mapCreateAssociationDtoToAssociation(CreateAssociationDto associationDto) {
        return DeviceMapper.INSTANCE.mapCreateAssociationDtoToAssociate(associationDto);
    }
}
