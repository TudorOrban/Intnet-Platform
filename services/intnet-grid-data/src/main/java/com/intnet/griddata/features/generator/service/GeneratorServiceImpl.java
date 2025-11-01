package com.intnet.griddata.features.generator.service;

import com.intnet.griddata.features.bus.model.Bus;
import com.intnet.griddata.features.bus.repository.BusRepository;
import com.intnet.griddata.features.generator.dto.*;
import com.intnet.griddata.features.generator.model.Generator;
import com.intnet.griddata.features.generator.model.GeneratorState;
import com.intnet.griddata.features.generator.repository.GeneratorRepository;
import com.intnet.griddata.shared.exception.types.ResourceIdentifierType;
import com.intnet.griddata.shared.exception.types.ResourceNotFoundException;
import com.intnet.griddata.shared.exception.types.ResourceType;
import com.intnet.griddata.shared.sanitization.service.EntitySanitizerService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * Service responsible for CRUD ops for the Generator feature
 */
@Service
public class GeneratorServiceImpl implements GeneratorService {

    private final GeneratorRepository generatorRepository;
    private final BusRepository busRepository;
    private final EntitySanitizerService sanitizerService;

    @Autowired
    public GeneratorServiceImpl(
            GeneratorRepository generatorRepository,
            BusRepository busRepository,
            EntitySanitizerService sanitizerService
    ) {
        this.generatorRepository = generatorRepository;
        this.busRepository = busRepository;
        this.sanitizerService = sanitizerService;
    }

    public List<GeneratorSearchDto> getGeneratorsByGridId(Long gridId) {
        List<Generator> generators = generatorRepository.findByGridId(gridId);

        return this.mapGeneratorsToGeneratorSearchDtos(generators);
    }

    public List<GeneratorSearchDto> getGeneratorsByBusId(Long busId) {
        List<Generator> generators = generatorRepository.findByBusId(busId);

        return this.mapGeneratorsToGeneratorSearchDtos(generators);
    }

    public GeneratorSearchDto getGeneratorById(Long id) {
        Generator generator = generatorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id.toString(), ResourceType.BUS, ResourceIdentifierType.ID));

        return this.mapGeneratorToGeneratorSearchDto(generator);
    }

    @Transactional
    public GeneratorSearchDto createGenerator(CreateGeneratorDto generatorDto) {
        CreateGeneratorDto sanitizedDto = sanitizerService.sanitizeCreateGeneratorDto(generatorDto);

        Generator generator = this.mapCreateGeneratorDtoToGenerator(sanitizedDto);

        Bus bus = busRepository.findById(generatorDto.getBusId())
                .orElseThrow(() -> new ResourceNotFoundException(generatorDto.getBusId().toString(), ResourceType.BUS, ResourceIdentifierType.ID));
        generator.setBus(bus);

        GeneratorState state = new GeneratorState();
        state.setGridId(generator.getGridId());
        state.setGenerator(generator);
        generator.setState(state);

        Generator savedGenerator = generatorRepository.save(generator);

        return this.mapGeneratorToGeneratorSearchDto(savedGenerator);
    }

    public GeneratorSearchDto updateGenerator(UpdateGeneratorDto generatorDto) {
        UpdateGeneratorDto sanitizedDto = sanitizerService.sanitizeUpdateGeneratorDto(generatorDto);

        Generator existingGenerator = generatorRepository.findById(sanitizedDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException(sanitizedDto.getId().toString(), ResourceType.BUS, ResourceIdentifierType.ID));

        this.setUpdateGeneratorDtoToGenerator(existingGenerator, sanitizedDto);

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
            GeneratorStateDto stateDto = this.mapStateToStateSearchDto(generator.getState());
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
        generator.setGeneratorMaxActivePower(generatorDto.getGeneratorMaxActivePower());
        generator.setGeneratorMinActivePower(generatorDto.getGeneratorMinActivePower());
        generator.setGeneratorMaxReactivePower(generatorDto.getGeneratorMaxReactivePower());
        generator.setGeneratorMinReactivePower(generatorDto.getGeneratorMinReactivePower());
    }

    private GeneratorStateDto mapStateToStateSearchDto(GeneratorState state) {
        return GeneratorMapper.INSTANCE.stateToStateSearchDto(state);
    }
}
