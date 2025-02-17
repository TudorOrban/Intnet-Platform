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

public interface EntitySanitizerService {

    CreateSubstationDto sanitizeCreateSubstationDto(CreateSubstationDto substationDto);
    UpdateSubstationDto sanitizeUpdateSubstationDto(UpdateSubstationDto substationDto);
    UpdateSubstationStateDto sanitizeUpdateSubstationStateDto(UpdateSubstationStateDto stateDto);

    CreateBusDto sanitizeCreateBusDto(CreateBusDto busDto);
    UpdateBusDto sanitizeUpdateBusDto(UpdateBusDto busDto);
    UpdateBusStateDto sanitizeUpdateBusStateDto(UpdateBusStateDto stateDto);

    CreateTransmissionLineDto sanitizeCreateTransmissionLineDto(CreateTransmissionLineDto lineDto);
    UpdateTransmissionLineDto sanitizeUpdateTransmissionLineDto(UpdateTransmissionLineDto lineDto);
    UpdateTransmissionLineStateDto sanitizeUpdateTransmissionLineStateDto(UpdateTransmissionLineStateDto stateDto);

    AddGridNodeDto sanitizeAddGridNodeDto(AddGridNodeDto nodeDto);
    AddGridEdgeDto sanitizeAddGridEdgeDto(AddGridEdgeDto edgeDto);

}
