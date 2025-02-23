package com.intnet.griddata.features.generator.service;

import com.intnet.griddata.features.generator.dto.*;
import com.intnet.griddata.features.generator.model.Generator;
import com.intnet.griddata.features.generator.model.GeneratorState;
import com.intnet.griddata.features.generator.repository.GeneratorRepository;
import com.intnet.griddata.shared.exception.types.ResourceIdentifierType;
import com.intnet.griddata.shared.exception.types.ResourceNotFoundException;
import com.intnet.griddata.shared.exception.types.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeneratorServiceImpl implements GeneratorService {

    private final GeneratorRepository generatorRepository;

    @Autowired
    public GeneratorServiceImpl(GeneratorRepository generatorRepository) {
        this.generatorRepository = generatorRepository;
    }

    public List<GeneratorSearchDto> getGeneratorsByGridId(Long gridId) {
        List<Generator> generators = generatorRepository.findByGridId(gridId);

        return this.mapGeneratorsToGeneratorSearchDtos(generators);
    }

    public GeneratorSearchDto getGeneratorById(Long id) {
        Generator generator = generatorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id.toString(), ResourceType.BUS, ResourceIdentifierType.ID));

        return this.mapGeneratorToGeneratorSearchDto(generator);
    }

    public GeneratorSearchDto createGenerator(CreateGeneratorDto generatorDto) {
        Generator generator = this.mapCreateGeneratorDtoToGenerator(generatorDto);

        Generator savedGenerator = generatorRepository.save(generator);

        return this.mapGeneratorToGeneratorSearchDto(savedGenerator);
    }

    public GeneratorSearchDto updateGenerator(UpdateGeneratorDto generatorDto) {
        Generator existingGenerator = generatorRepository.findById(generatorDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException(generatorDto.getId().toString(), ResourceType.BUS, ResourceIdentifierType.ID));

        this.setUpdateGeneratorDtoToGenerator(existingGenerator, generatorDto);

        Generator savedGenerator = generatorRepository.save(existingGenerator);

        return this.mapGeneratorToGeneratorSearchDto(savedGenerator);
    }

    public void deleteGenerator(Long id) {
        Generator existingGenerator = generatorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id.toString(), ResourceType.BUS, ResourceIdentifierType.ID));

        generatorRepository.delete(existingGenerator);
    }

    public List<GeneratorSearchDto> mapGeneratorsToGeneratorSearchDtos(List<Generator> generators) {
        if (generators == null) return null;

        return generators.stream().map(generator -> {
            GeneratorSearchDto generatorDto = this.mapGeneratorToGeneratorSearchDto(generator);
            GeneratorStateSearchDto stateDto = this.mapStateToStateSearchDto(generator.getGeneratorState());
            generatorDto.setState(stateDto);
            return generatorDto;
        }).toList();
    }

    private GeneratorSearchDto mapGeneratorToGeneratorSearchDto(Generator generator) {
        return GeneratorMapper.INSTANCE.generatorToGeneratorSearchDto(generator);
    }

    private Generator mapCreateGeneratorDtoToGenerator(CreateGeneratorDto generatorDto) {
        return GeneratorMapper.INSTANCE.createGeneratorDtoToGenerator(generatorDto);
    }

    private void setUpdateGeneratorDtoToGenerator(Generator generator, UpdateGeneratorDto generatorDto) {
        generator.setGeneratorName(generatorDto.getGeneratorName());
        generator.setGeneratorVoltageSetpoint(generatorDto.getGeneratorVoltageSetpoint());
        generator.setGeneratorMaxActivePower(generatorDto.getGeneratorMaxActivePower());
        generator.setGeneratorMinActivePower(generatorDto.getGeneratorMinActivePower());
        generator.setGeneratorMaxReactivePower(generatorDto.getGeneratorMaxReactivePower());
        generator.setGeneratorMinReactivePower(generatorDto.getGeneratorMinReactivePower());
    }

    private GeneratorStateSearchDto mapStateToStateSearchDto(GeneratorState state) {
        return GeneratorMapper.INSTANCE.stateToStateSearchDto(state);
    }
}
