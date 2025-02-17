package com.intnet.griddata.features.transmissionline.service;

import com.intnet.griddata.core.internal.out.gridtopology.service.GridGraphUpdaterService;
import com.intnet.griddata.features.transmissionline.dto.*;
import com.intnet.griddata.features.transmissionline.model.TransmissionLine;
import com.intnet.griddata.features.transmissionline.model.TransmissionLineState;
import com.intnet.griddata.features.transmissionline.repository.TransmissionLineRepository;
import com.intnet.griddata.features.transmissionline.repository.TransmissionLineStateRepository;
import com.intnet.griddata.shared.exception.types.ResourceIdentifierType;
import com.intnet.griddata.shared.exception.types.ResourceNotFoundException;
import com.intnet.griddata.shared.exception.types.ResourceType;
import com.intnet.griddata.shared.sanitization.service.EntitySanitizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransmissionLineServiceImpl implements TransmissionLineService {

    private final TransmissionLineRepository transmissionLineRepository;
    private final TransmissionLineStateRepository transmissionLineStateRepository;
    private final GridGraphUpdaterService graphUpdaterService;
    private final EntitySanitizerService sanitizerService;

    @Autowired
    public TransmissionLineServiceImpl(
            TransmissionLineRepository transmissionLineRepository,
            TransmissionLineStateRepository transmissionLineStateRepository,
            GridGraphUpdaterService graphUpdaterService,
            EntitySanitizerService sanitizerService
    ) {
        this.transmissionLineRepository = transmissionLineRepository;
        this.transmissionLineStateRepository = transmissionLineStateRepository;
        this.graphUpdaterService = graphUpdaterService;
        this.sanitizerService = sanitizerService;
    }

    public TransmissionLineSearchDto getTransmissionLineById(Long id, Boolean attachState) {
        TransmissionLine transmissionLine = transmissionLineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id.toString(), ResourceType.BUS, ResourceIdentifierType.ID));

        TransmissionLineStateDto stateDto = null;
        if (attachState) {
            TransmissionLineState state = transmissionLineStateRepository.findByTransmissionLineId(transmissionLine.getId())
                    .orElseThrow(() -> new ResourceNotFoundException(transmissionLine.getId().toString(), ResourceType.BUS_STATE, ResourceIdentifierType.BUS_ID));
            stateDto = this.mapTransmissionLineStateToTransmissionLineStateDto(state);
        }

        return this.mapTransmissionLineToTransmissionLineSearchDto(transmissionLine, stateDto);
    }

    public TransmissionLineSearchDto createTransmissionLine(CreateTransmissionLineDto transmissionLineDto) {
        CreateTransmissionLineDto sanitizedLineDto = sanitizerService.sanitizeCreateTransmissionLineDto(transmissionLineDto);

        TransmissionLine transmissionLine = this.mapCreateTransmissionLineDtoToTransmissionLine(sanitizedLineDto);

        TransmissionLine savedTransmissionLine = transmissionLineRepository.save(transmissionLine);

        TransmissionLineStateDto stateDto = this.createTransmissionLineState(savedTransmissionLine);

        graphUpdaterService.updateGraph(savedTransmissionLine);

        return this.mapTransmissionLineToTransmissionLineSearchDto(savedTransmissionLine, stateDto);
    }

    private TransmissionLineStateDto createTransmissionLineState(TransmissionLine savedTransmissionLine) {
        TransmissionLineState transmissionLineState = new TransmissionLineState();
        transmissionLineState.setGridId(savedTransmissionLine.getGridId());
        transmissionLineState.setTransmissionLineId(savedTransmissionLine.getId());

        TransmissionLineState savedState = transmissionLineStateRepository.save(transmissionLineState);
        return this.mapTransmissionLineStateToTransmissionLineStateDto(savedState);
    }

    public TransmissionLineSearchDto updateTransmissionLine(UpdateTransmissionLineDto transmissionLineDto) {
        UpdateTransmissionLineDto sanitizedLineDto = sanitizerService.sanitizeUpdateTransmissionLineDto(transmissionLineDto);

        TransmissionLine transmissionLine = transmissionLineRepository.findById(sanitizedLineDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException(sanitizedLineDto.getId().toString(), ResourceType.BUS, ResourceIdentifierType.ID));

        this.setUpdateTransmissionLineDtoToTransmissionLine(transmissionLine, sanitizedLineDto);

        TransmissionLine savedTransmissionLine = transmissionLineRepository.save(transmissionLine);

        return this.mapTransmissionLineToTransmissionLineSearchDto(savedTransmissionLine, null);
    }

    public void deleteTransmissionLine(Long id) {
        TransmissionLine transmissionLine = transmissionLineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id.toString(), ResourceType.BUS, ResourceIdentifierType.ID));

        this.transmissionLineRepository.delete(transmissionLine);
    }

    private TransmissionLineSearchDto mapTransmissionLineToTransmissionLineSearchDto(TransmissionLine transmissionLine, TransmissionLineStateDto stateDto) {
        TransmissionLineSearchDto transmissionLineDto = TransmissionLineMapper.INSTANCE.transmissionLineToTransmissionLineSearchDto(transmissionLine);
        transmissionLineDto.setState(stateDto);
        return transmissionLineDto;
    }

    private TransmissionLine mapCreateTransmissionLineDtoToTransmissionLine(CreateTransmissionLineDto transmissionLineDto) {
        return TransmissionLineMapper.INSTANCE.createTransmissionLineDtoToTransmissionLine(transmissionLineDto);
    }

    private TransmissionLineStateDto mapTransmissionLineStateToTransmissionLineStateDto(TransmissionLineState state) {
        return TransmissionLineMapper.INSTANCE.transmissionLineStateToTransmissionLineStateDto(state);
    }

    private void setUpdateTransmissionLineDtoToTransmissionLine(TransmissionLine transmissionLine, UpdateTransmissionLineDto lineDto) {
        transmissionLine.setEdgeType(lineDto.getEdgeType());
        transmissionLine.setLength(lineDto.getLength());
        transmissionLine.setImpedance(lineDto.getImpedance());
        transmissionLine.setAdmittance(lineDto.getAdmittance());
        transmissionLine.setCapacity(lineDto.getCapacity());
    }
}
