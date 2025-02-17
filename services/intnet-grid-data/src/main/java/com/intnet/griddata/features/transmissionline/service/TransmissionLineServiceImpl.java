package com.intnet.griddata.features.transmissionline.service;

import com.intnet.griddata.features.transmissionline.dto.*;
import com.intnet.griddata.features.transmissionline.model.TransmissionLine;
import com.intnet.griddata.features.transmissionline.model.TransmissionLineState;
import com.intnet.griddata.features.transmissionline.repository.TransmissionLineRepository;
import com.intnet.griddata.features.transmissionline.repository.TransmissionLineStateRepository;
import com.intnet.griddata.shared.exception.types.ResourceIdentifierType;
import com.intnet.griddata.shared.exception.types.ResourceNotFoundException;
import com.intnet.griddata.shared.exception.types.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransmissionLineServiceImpl implements TransmissionLineService {

    private final TransmissionLineRepository transmissionLineRepository;
    private final TransmissionLineStateRepository transmissionLineStateRepository;

    @Autowired
    public TransmissionLineServiceImpl(
            TransmissionLineRepository transmissionLineRepository,
            TransmissionLineStateRepository transmissionLineStateRepository
    ) {
        this.transmissionLineRepository = transmissionLineRepository;
        this.transmissionLineStateRepository = transmissionLineStateRepository;
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
        TransmissionLine transmissionLine = this.mapCreateTransmissionLineDtoToTransmissionLine(transmissionLineDto);

        TransmissionLine savedTransmissionLine = transmissionLineRepository.save(transmissionLine);

        TransmissionLineStateDto stateDto = this.createTransmissionLineState(savedTransmissionLine);

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
        TransmissionLine transmissionLine = transmissionLineRepository.findById(transmissionLineDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException(transmissionLineDto.getId().toString(), ResourceType.BUS, ResourceIdentifierType.ID));

        transmissionLine.setEdgeType(transmissionLineDto.getEdgeType());
        transmissionLine.setLength(transmissionLineDto.getLength());
        transmissionLine.setImpedance(transmissionLineDto.getImpedance());
        transmissionLine.setAdmittance(transmissionLineDto.getAdmittance());
        transmissionLine.setCapacity(transmissionLineDto.getCapacity());

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
}
