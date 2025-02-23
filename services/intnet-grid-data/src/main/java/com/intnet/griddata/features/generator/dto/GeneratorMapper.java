package com.intnet.griddata.features.generator.dto;

import com.intnet.griddata.features.generator.model.Generator;
import com.intnet.griddata.features.generator.model.GeneratorState;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GeneratorMapper {
    GeneratorMapper INSTANCE = Mappers.getMapper(GeneratorMapper.class);

    @Mapping(source = "generator.id", target = "id")
    @Mapping(source = "generator.gridId", target = "gridId")
    @Mapping(source = "generator.generatorName", target = "generatorName")
    @Mapping(source = "generator.createdAt", target = "createdAt")
    @Mapping(source = "generator.updatedAt", target = "updatedAt")
    @Mapping(source = "generator.generatorVoltageSetpoint", target = "generatorVoltageSetpoint")
    @Mapping(source = "generator.generatorMaxActivePower", target = "generatorMaxActivePower")
    @Mapping(source = "generator.generatorMinActivePower", target = "generatorMinActivePower")
    @Mapping(source = "generator.generatorMaxReactivePower", target = "generatorMaxReactivePower")
    @Mapping(source = "generator.generatorMinReactivePower", target = "generatorMinReactivePower")
    GeneratorSearchDto generatorToGeneratorSearchDto(Generator generator);

    @Mapping(source = "gridId", target = "gridId")
    @Mapping(source = "generatorName", target = "generatorName")
    @Mapping(source = "generatorVoltageSetpoint", target = "generatorVoltageSetpoint")
    @Mapping(source = "generatorMaxActivePower", target = "generatorMaxActivePower")
    @Mapping(source = "generatorMinActivePower", target = "generatorMinActivePower")
    @Mapping(source = "generatorMaxReactivePower", target = "generatorMaxReactivePower")
    @Mapping(source = "generatorMinReactivePower", target = "generatorMinReactivePower")
    Generator createGeneratorDtoToGenerator(CreateGeneratorDto generatorDto);

    @Mapping(source = "state.id", target = "id")
    @Mapping(source = "state.gridId", target = "gridId")
    @Mapping(source = "state.updatedAt", target = "updatedAt")
    @Mapping(source = "state.activePowerGeneration", target = "activePowerGeneration")
    @Mapping(source = "state.reactivePowerGeneration", target = "reactivePowerGeneration")
    @Mapping(source = "state.generatorVoltageSetpoint", target = "generatorVoltageSetpoint")
    GeneratorStateDto stateToStateSearchDto(GeneratorState state);
}
