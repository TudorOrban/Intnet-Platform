package com.intnet.griddata.features.generator.service;

import com.intnet.griddata.features.generator.dto.CreateGeneratorDto;
import com.intnet.griddata.features.generator.dto.GeneratorSearchDto;
import com.intnet.griddata.features.generator.dto.UpdateGeneratorDto;
import com.intnet.griddata.features.generator.model.Generator;

import java.util.List;

public interface GeneratorService {

    List<GeneratorSearchDto> getGeneratorsByGridId(Long gridId);
    GeneratorSearchDto getGeneratorById(Long id);
    GeneratorSearchDto createGenerator(CreateGeneratorDto generatorDto);
    GeneratorSearchDto updateGenerator(UpdateGeneratorDto generatorDto);
    void deleteGenerator(Long id);
    List<GeneratorSearchDto> mapGeneratorsToGeneratorSearchDtos(List<Generator> generators);
}
