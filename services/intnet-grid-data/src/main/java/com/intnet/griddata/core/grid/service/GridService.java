package com.intnet.griddata.core.grid.service;

import com.intnet.griddata.core.grid.dto.CreateGridDto;
import com.intnet.griddata.core.grid.dto.GridSearchDto;
import com.intnet.griddata.core.grid.dto.UpdateGridDto;

import java.util.List;

public interface GridService {

    List<GridSearchDto> getGrids();
    GridSearchDto createGrid(CreateGridDto gridDto);
    GridSearchDto updateGrid(UpdateGridDto gridDto);
    void deleteGrid(Long id);
}
