package com.intnet.griddata.features.transmissionline.service;

import com.intnet.griddata.features.transmissionline.dto.TransmissionLineMapper;
import com.intnet.griddata.features.transmissionline.dto.TransmissionLineStateDto;
import com.intnet.griddata.features.transmissionline.dto.UpdateTransmissionLineStateDto;
import com.intnet.griddata.features.transmissionline.model.TransmissionLineState;
import com.intnet.griddata.features.transmissionline.repository.TransmissionLineStateRepository;
import com.intnet.griddata.shared.exception.types.ResourceIdentifierType;
import com.intnet.griddata.shared.exception.types.ResourceNotFoundException;
import com.intnet.griddata.shared.exception.types.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransmissionLineStateUpdaterServiceImpl implements TransmissionLineStateUpdaterService {

    private final TransmissionLineStateRepository transmissionLineStateRepository;

    @Autowired
    public TransmissionLineStateUpdaterServiceImpl(
            TransmissionLineStateRepository transmissionLineStateRepository
    ) {
        this.transmissionLineStateRepository = transmissionLineStateRepository;
    }

    public TransmissionLineStateDto updateTransmissionLineState(UpdateTransmissionLineStateDto stateDto) {
        TransmissionLineState state = transmissionLineStateRepository.findByTransmissionLineId(stateDto.getTransmissionLineId())
                .orElseThrow(() -> new ResourceNotFoundException(stateDto.getTransmissionLineId().toString(), ResourceType.BUS_STATE, ResourceIdentifierType.BUS_ID));

        state.setCurrent(stateDto.getCurrent());
        state.setPowerFlowActive(stateDto.getPowerFlowActive());
        state.setPowerFlowReactive(stateDto.getPowerFlowReactive());
        state.setTemperature(stateDto.getTemperature());

        TransmissionLineState savedState = transmissionLineStateRepository.save(state);

        return this.mapTransmissionLineStateToTransmissionLineStateDto(savedState);
    }

    private TransmissionLineStateDto mapTransmissionLineStateToTransmissionLineStateDto(TransmissionLineState state) {
        return TransmissionLineMapper.INSTANCE.transmissionLineStateToTransmissionLineStateDto(state);
    }
}
