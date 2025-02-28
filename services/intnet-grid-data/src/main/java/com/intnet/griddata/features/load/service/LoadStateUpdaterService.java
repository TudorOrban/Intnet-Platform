package com.intnet.griddata.features.load.service;

import com.intnet.griddata.features.load.dto.LoadStateDto;
import com.intnet.griddata.features.load.dto.UpdateLoadStateDto;

public interface LoadStateUpdaterService {

    LoadStateDto updateLoadState(UpdateLoadStateDto stateDto);
}
