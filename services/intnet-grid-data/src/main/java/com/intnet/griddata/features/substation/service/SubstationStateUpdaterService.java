package com.intnet.griddata.features.substation.service;

import com.intnet.griddata.features.substation.dto.SubstationStateDto;
import com.intnet.griddata.features.substation.dto.UpdateSubstationStateDto;

public interface SubstationStateUpdaterService {

    SubstationStateDto updateSubstationState(UpdateSubstationStateDto stateDto);
}
