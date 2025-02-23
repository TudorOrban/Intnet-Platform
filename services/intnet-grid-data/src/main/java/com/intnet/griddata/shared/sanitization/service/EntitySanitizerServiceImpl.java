package com.intnet.griddata.shared.sanitization.service;

import com.intnet.griddata.core.internal.out.gridtopology.dto.AddGridEdgeDto;
import com.intnet.griddata.core.internal.out.gridtopology.dto.AddGridNodeDto;
import com.intnet.griddata.features.bus.dto.CreateBusDto;
import com.intnet.griddata.features.bus.dto.UpdateBusDto;
import com.intnet.griddata.features.bus.dto.UpdateBusStateDto;
import com.intnet.griddata.features.edge.dto.CreateEdgeDto;
import com.intnet.griddata.features.edge.dto.UpdateEdgeDto;
import com.intnet.griddata.features.edge.dto.UpdateEdgeStateDto;
import com.intnet.griddata.features.generator.dto.CreateGeneratorDto;
import com.intnet.griddata.features.generator.dto.UpdateGeneratorDto;
import com.intnet.griddata.features.generator.dto.UpdateGeneratorStateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntitySanitizerServiceImpl implements EntitySanitizerService {

    private final SanitizationService sanitizationService;

    @Autowired
    public EntitySanitizerServiceImpl(SanitizationService sanitizationService) {
        this.sanitizationService = sanitizationService;
    }

    public CreateBusDto sanitizeCreateBusDto(CreateBusDto busDto) {
        busDto.setBusName(sanitizationService.sanitize(busDto.getBusName()));
        return busDto;
    }

    public UpdateBusDto sanitizeUpdateBusDto(UpdateBusDto busDto) {
        busDto.setBusName(sanitizationService.sanitize(busDto.getBusName()));
        return busDto;
    }

    public UpdateBusStateDto sanitizeUpdateBusStateDto(UpdateBusStateDto stateDto) {
        return stateDto;
    }

    public CreateGeneratorDto sanitizeCreateGeneratorDto(CreateGeneratorDto generatorDto) {
        generatorDto.setGeneratorName(sanitizationService.sanitize(generatorDto.getGeneratorName()));
        return generatorDto;
    }

    public UpdateGeneratorDto sanitizeUpdateGeneratorDto(UpdateGeneratorDto generatorDto) {
        generatorDto.setGeneratorName(sanitizationService.sanitize(generatorDto.getGeneratorName()));
        return generatorDto;
    }

    public UpdateGeneratorStateDto sanitizeUpdateGeneratorStateDto(UpdateGeneratorStateDto stateDto) {
        return stateDto;
    }


    public CreateEdgeDto sanitizeCreateEdgeDto(CreateEdgeDto busDto) {
        busDto.setEdgeName(sanitizationService.sanitize(busDto.getEdgeName()));
        return busDto;
    }

    public UpdateEdgeDto sanitizeUpdateEdgeDto(UpdateEdgeDto busDto) {
        busDto.setEdgeName(sanitizationService.sanitize(busDto.getEdgeName()));
        return busDto;
    }

    public UpdateEdgeStateDto sanitizeUpdateEdgeStateDto(UpdateEdgeStateDto stateDto) {
        return stateDto;
    }

    public AddGridNodeDto sanitizeAddGridNodeDto(AddGridNodeDto nodeDto) {
        return nodeDto;
    }

    public AddGridEdgeDto sanitizeAddGridEdgeDto(AddGridEdgeDto edgeDto) {
        return edgeDto;
    }
}
