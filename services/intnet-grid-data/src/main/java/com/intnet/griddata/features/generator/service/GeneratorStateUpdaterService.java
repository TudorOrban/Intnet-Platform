package com.intnet.griddata.features.generator.service;

import com.intnet.griddata.features.generator.dto.GeneratorStateDto;
import com.intnet.griddata.features.generator.dto.UpdateGeneratorStateDto;

public interface GeneratorStateUpdaterService {

    GeneratorStateDto updateGeneratorState(UpdateGeneratorStateDto stateDto);
}
