package com.intnet.griddata.features.load.service;

import com.intnet.griddata.features.load.dto.CreateLoadDto;
import com.intnet.griddata.features.load.dto.LoadSearchDto;
import com.intnet.griddata.features.load.dto.UpdateLoadDto;
import com.intnet.griddata.features.load.model.Load;

import java.util.List;

public interface LoadService {

    List<LoadSearchDto> getLoadsByGridId(Long gridId);
    List<LoadSearchDto> getLoadsByBusId(Long busId);
    LoadSearchDto getLoadById(Long id);
    LoadSearchDto createLoad(CreateLoadDto loadDto);
    LoadSearchDto updateLoad(UpdateLoadDto loadDto);
    void deleteLoad(Long id);
    List<LoadSearchDto> mapLoadsToLoadSearchDtos(List<Load> loads);
}
