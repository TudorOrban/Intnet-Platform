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

public interface EntitySanitizerService {

    CreateBusDto sanitizeCreateBusDto(CreateBusDto busDto);
    UpdateBusDto sanitizeUpdateBusDto(UpdateBusDto busDto);
    UpdateBusStateDto sanitizeUpdateBusStateDto(UpdateBusStateDto stateDto);

    CreateGeneratorDto sanitizeCreateGeneratorDto(CreateGeneratorDto generatorDto);
    UpdateGeneratorDto sanitizeUpdateGeneratorDto(UpdateGeneratorDto generatorDto);
    UpdateGeneratorStateDto sanitizeUpdateGeneratorStateDto(UpdateGeneratorStateDto stateDto);

    CreateEdgeDto sanitizeCreateEdgeDto(CreateEdgeDto busDto);
    UpdateEdgeDto sanitizeUpdateEdgeDto(UpdateEdgeDto busDto);
    UpdateEdgeStateDto sanitizeUpdateEdgeStateDto(UpdateEdgeStateDto stateDto);

    AddGridNodeDto sanitizeAddGridNodeDto(AddGridNodeDto nodeDto);
    AddGridEdgeDto sanitizeAddGridEdgeDto(AddGridEdgeDto edgeDto);

}
