package com.intnet.griddata.shared.sanitization.service;

import com.intnet.griddata.core.internal.out.gridtopology.dto.AddGridEdgeDto;
import com.intnet.griddata.core.internal.out.gridtopology.dto.AddGridNodeDto;
import com.intnet.griddata.features.bus.dto.CreateBusDto;
import com.intnet.griddata.features.bus.dto.UpdateBusDto;
import com.intnet.griddata.features.bus.dto.UpdateBusStateDto;

public interface EntitySanitizerService {

    CreateBusDto sanitizeCreateBusDto(CreateBusDto busDto);
    UpdateBusDto sanitizeUpdateBusDto(UpdateBusDto busDto);
    UpdateBusStateDto sanitizeUpdateBusStateDto(UpdateBusStateDto stateDto);

    AddGridNodeDto sanitizeAddGridNodeDto(AddGridNodeDto nodeDto);
    AddGridEdgeDto sanitizeAddGridEdgeDto(AddGridEdgeDto edgeDto);

}
