package com.intnet.griddata.features.transmissionline.service;

import com.intnet.griddata.features.transmissionline.dto.TransmissionLineMapper;
import com.intnet.griddata.features.transmissionline.dto.TransmissionLineStateDto;
import com.intnet.griddata.features.transmissionline.dto.UpdateTransmissionLineDto;
import com.intnet.griddata.features.transmissionline.dto.UpdateTransmissionLineStateDto;
import com.intnet.griddata.features.transmissionline.model.TransmissionLineState;
import com.intnet.griddata.features.transmissionline.repository.TransmissionLineStateRepository;
import com.intnet.griddata.shared.exception.types.ResourceIdentifierType;
import com.intnet.griddata.shared.exception.types.ResourceNotFoundException;
import com.intnet.griddata.shared.exception.types.ResourceType;
import com.intnet.griddata.shared.sanitization.service.EntitySanitizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransmissionLineStateUpdaterServiceImpl implements TransmissionLineStateUpdaterService {

    private final TransmissionLineStateRepository transmissionLineStateRepository;
    private final EntitySanitizerService sanitizerService;

    @Autowired
    public TransmissionLineStateUpdaterServiceImpl(
            TransmissionLineStateRepository transmissionLineStateRepository,
            EntitySanitizerService sanitizerService
    ) {
        this.transmissionLineStateRepository = transmissionLineStateRepository;
        this.sanitizerService = sanitizerService;
    }

    public TransmissionLineStateDto updateTransmissionLineState(UpdateTransmissionLineStateDto stateDto) {
        UpdateTransmissionLineStateDto sanitizedStateDto = sanitizerService.sanitizeUpdateTransmissionLineStateDto(stateDto);
        TransmissionLineState state = transmissionLineStateRepository.findByTransmissionLineId(sanitizedStateDto.getTransmissionLineId())
                .orElseThrow(() -> new ResourceNotFoundException(sanitizedStateDto.getTransmissionLineId().toString(), ResourceType.BUS_STATE, ResourceIdentifierType.BUS_ID));

        this.setUpdateTransmissionLineStateDtoToTransmissionLineState(state, sanitizedStateDto);

        TransmissionLineState savedState = transmissionLineStateRepository.save(state);

        return this.mapTransmissionLineStateToTransmissionLineStateDto(savedState);
    }

    private TransmissionLineStateDto mapTransmissionLineStateToTransmissionLineStateDto(TransmissionLineState state) {
        return TransmissionLineMapper.INSTANCE.transmissionLineStateToTransmissionLineStateDto(state);
    }

    private void setUpdateTransmissionLineStateDtoToTransmissionLineState(TransmissionLineState state, UpdateTransmissionLineStateDto stateDto) {
        state.setCurrent(stateDto.getCurrent());
        state.setPowerFlowActive(stateDto.getPowerFlowActive());
        state.setPowerFlowReactive(stateDto.getPowerFlowReactive());
        state.setTemperature(stateDto.getTemperature());
    }
}
