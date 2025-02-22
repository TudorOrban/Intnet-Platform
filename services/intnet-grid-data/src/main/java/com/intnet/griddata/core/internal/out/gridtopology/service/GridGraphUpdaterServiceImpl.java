package com.intnet.griddata.core.internal.out.gridtopology.service;

import com.intnet.griddata.core.internal.out.gridtopology.dto.*;
import com.intnet.griddata.features.bus.model.Bus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GridGraphUpdaterServiceImpl implements GridGraphUpdaterService {

    private final GridTopologyCommunicatorService communicatorService;

    @Autowired
    public GridGraphUpdaterServiceImpl(
            GridTopologyCommunicatorServiceImpl communicatorService
    ) {
        this.communicatorService = communicatorService;
    }

    public GridNodeDto updateGraph(Bus newBus) {
        AddGridNodeDto nodeDto = GridMapper.INSTANCE.busToAddGridNodeDto(newBus);

        return communicatorService.addNode(nodeDto);
    }
}
