package com.intnet.griddata.shared.sanitization.service;

import com.intnet.griddata.core.internal.out.gridtopology.dto.AddGridEdgeDto;
import com.intnet.griddata.core.internal.out.gridtopology.dto.AddGridNodeDto;
import com.intnet.griddata.features.bus.dto.CreateBusDto;
import com.intnet.griddata.features.bus.dto.UpdateBusDto;
import com.intnet.griddata.features.bus.dto.UpdateBusStateDto;
import com.intnet.griddata.features.substation.dto.CreateSubstationDto;
import com.intnet.griddata.features.substation.dto.UpdateSubstationDto;
import com.intnet.griddata.features.substation.dto.UpdateSubstationStateDto;
import com.intnet.griddata.features.transmissionline.dto.CreateTransmissionLineDto;
import com.intnet.griddata.features.transmissionline.dto.UpdateTransmissionLineDto;
import com.intnet.griddata.features.transmissionline.dto.UpdateTransmissionLineStateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntitySanitizerServiceImpl implements EntitySanitizerService {

    private final SanitizationService sanitizationService;

    @Autowired
    public EntitySanitizerServiceImpl(SanitizationService sanitizationService) {
        this.sanitizationService = sanitizationService;
    }

    public CreateSubstationDto sanitizeCreateSubstationDto(CreateSubstationDto substationDto) {
        return substationDto;
    }

    public UpdateSubstationDto sanitizeUpdateSubstationDto(UpdateSubstationDto substationDto) {
        return substationDto;
    }

    public UpdateSubstationStateDto sanitizeUpdateSubstationStateDto(UpdateSubstationStateDto stateDto) {
        return stateDto;
    }

    public CreateBusDto sanitizeCreateBusDto(CreateBusDto busDto) {
        return busDto;
    }

    public UpdateBusDto sanitizeUpdateBusDto(UpdateBusDto busDto) {
        return busDto;
    }

    public UpdateBusStateDto sanitizeUpdateBusStateDto(UpdateBusStateDto stateDto) {
        return stateDto;
    }

    public CreateTransmissionLineDto sanitizeCreateTransmissionLineDto(CreateTransmissionLineDto lineDto) {
        return lineDto;
    }

    public UpdateTransmissionLineDto sanitizeUpdateTransmissionLineDto(UpdateTransmissionLineDto lineDto) {
        return lineDto;
    }

    public UpdateTransmissionLineStateDto sanitizeUpdateTransmissionLineStateDto(UpdateTransmissionLineStateDto stateDto) {
        return stateDto;
    }

    public AddGridNodeDto sanitizeAddGridNodeDto(AddGridNodeDto nodeDto) {
        return nodeDto;
    }

    public AddGridEdgeDto sanitizeAddGridEdgeDto(AddGridEdgeDto edgeDto) {
        return edgeDto;
    }
}
