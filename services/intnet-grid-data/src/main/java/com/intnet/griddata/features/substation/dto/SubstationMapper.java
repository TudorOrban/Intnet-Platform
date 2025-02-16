package com.intnet.griddata.features.substation.dto;

import com.intnet.griddata.features.substation.model.Substation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SubstationMapper {
    SubstationMapper INSTANCE = Mappers.getMapper(SubstationMapper.class);

    @Mapping(source = "substation.id", target = "id")
    @Mapping(source = "substation.gridId", target = "gridId")
    @Mapping(source = "substation.createdAt", target = "createdAt")
    @Mapping(source = "substation.updatedAt", target = "updatedAt")
    @Mapping(source = "substation.latitude", target = "latitude")
    @Mapping(source = "substation.longitude", target = "longitude")
    @Mapping(source = "substation.transformers", target = "transformers")
    @Mapping(source = "substation.connectedBuses", target = "connectedBuses")
    SubstationSearchDto substationToSubstationSearchDto(Substation substation);

    @Mapping(source = "gridId", target = "gridId")
    @Mapping(source = "latitude", target = "latitude")
    @Mapping(source = "longitude", target = "longitude")
    @Mapping(source = "transformers", target = "transformers")
    @Mapping(source = "connectedBuses", target = "connectedBuses")
    Substation createSubstationDtoToSubstation(CreateSubstationDto substationDto);

}
