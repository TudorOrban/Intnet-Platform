package com.intnet.griddata.features.transmissionline.service;

import com.intnet.griddata.features.transmissionline.dto.TransmissionLineStateDto;
import com.intnet.griddata.features.transmissionline.dto.UpdateTransmissionLineStateDto;

public interface TransmissionLineStateUpdaterService {

    TransmissionLineStateDto updateTransmissionLineState(UpdateTransmissionLineStateDto stateDto);
}
