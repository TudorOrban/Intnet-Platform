package com.intnet.griddata.features.transmissionline.service;

import com.intnet.griddata.features.transmissionline.dto.TransmissionLineSearchDto;
import com.intnet.griddata.features.transmissionline.dto.CreateTransmissionLineDto;
import com.intnet.griddata.features.transmissionline.dto.UpdateTransmissionLineDto;

public interface TransmissionLineService {

    TransmissionLineSearchDto getTransmissionLineById(Long id, Boolean attachState);
    TransmissionLineSearchDto createTransmissionLine(CreateTransmissionLineDto transmissionLineDto);
    TransmissionLineSearchDto updateTransmissionLine(UpdateTransmissionLineDto transmissionLineDto);
    void deleteTransmissionLine(Long id);
}
