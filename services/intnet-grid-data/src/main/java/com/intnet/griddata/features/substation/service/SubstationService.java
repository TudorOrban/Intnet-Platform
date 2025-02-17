package com.intnet.griddata.features.substation.service;

import com.intnet.griddata.features.substation.dto.CreateSubstationDto;
import com.intnet.griddata.features.substation.dto.SubstationSearchDto;
import com.intnet.griddata.features.substation.dto.UpdateSubstationDto;

public interface SubstationService {

    SubstationSearchDto getSubstationById(Long id, Boolean attachState);
    SubstationSearchDto createSubstation(CreateSubstationDto substationDto);
    SubstationSearchDto updateSubstation(UpdateSubstationDto substationDto);
    void deleteSubstation(Long id);
}
