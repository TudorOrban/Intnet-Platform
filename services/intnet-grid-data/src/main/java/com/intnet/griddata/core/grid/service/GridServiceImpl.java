package com.intnet.griddata.core.grid.service;

import com.intnet.griddata.core.grid.dto.CreateGridDto;
import com.intnet.griddata.core.grid.dto.GridMapper;
import com.intnet.griddata.core.grid.dto.GridSearchDto;
import com.intnet.griddata.core.grid.dto.UpdateGridDto;
import com.intnet.griddata.core.grid.model.Grid;
import com.intnet.griddata.core.grid.repository.GridRepository;
import com.intnet.griddata.shared.exception.types.ResourceIdentifierType;
import com.intnet.griddata.shared.exception.types.ResourceNotFoundException;
import com.intnet.griddata.shared.exception.types.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * Service responsible for CRUD ops for the Grid entity
 */
@Service
public class GridServiceImpl implements GridService {

    private final GridRepository gridRepository;

    @Autowired
    public GridServiceImpl(GridRepository gridRepository) {
        this.gridRepository = gridRepository;
    }

    public List<GridSearchDto> getGrids() {
        List<Grid> grids = gridRepository.findAll();

        return grids.stream().map(this::mapGridToGridSearchDto).toList();
    }

    public GridSearchDto createGrid(CreateGridDto gridDto) {
        Grid grid = this.mapCreateGridDtoToGrid(gridDto);

        Grid savedGrid = gridRepository.save(grid);

        return this.mapGridToGridSearchDto(savedGrid);
    }

    public GridSearchDto updateGrid(UpdateGridDto gridDto) {
        Grid existingGrid = gridRepository.findById(gridDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException(gridDto.getId().toString(), ResourceType.GRID, ResourceIdentifierType.ID));

        existingGrid.setName(gridDto.getName());
        existingGrid.setDescription(gridDto.getDescription());

        Grid savedGrid = gridRepository.save(existingGrid);

        return this.mapGridToGridSearchDto(savedGrid);
    }

    public void deleteGrid(Long id) {
        Grid existingGrid = gridRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id.toString(), ResourceType.GRID, ResourceIdentifierType.ID));

        gridRepository.delete(existingGrid);
    }

    private GridSearchDto mapGridToGridSearchDto(Grid grid) {
        return GridMapper.INSTANCE.gridToGridSearchDto(grid);
    }

    private Grid mapCreateGridDtoToGrid(CreateGridDto gridDto) {
        return GridMapper.INSTANCE.gridToGridSearchDto(gridDto);
    }
}
