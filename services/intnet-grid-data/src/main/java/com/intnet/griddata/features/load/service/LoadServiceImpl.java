package com.intnet.griddata.features.load.service;

import com.intnet.griddata.features.bus.model.Bus;
import com.intnet.griddata.features.bus.repository.BusRepository;
import com.intnet.griddata.features.load.dto.*;
import com.intnet.griddata.features.load.model.Load;
import com.intnet.griddata.features.load.model.LoadState;
import com.intnet.griddata.features.load.repository.LoadRepository;
import com.intnet.griddata.shared.exception.types.ResourceIdentifierType;
import com.intnet.griddata.shared.exception.types.ResourceNotFoundException;
import com.intnet.griddata.shared.exception.types.ResourceType;
import com.intnet.griddata.shared.sanitization.service.EntitySanitizerService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * Service responsible for CRUD ops for the Load feature
 */
@Service
public class LoadServiceImpl implements LoadService {

    private final LoadRepository loadRepository;
    private final BusRepository busRepository;
    private final EntitySanitizerService sanitizerService;

    @Autowired
    public LoadServiceImpl(
            LoadRepository loadRepository,
            BusRepository busRepository,
            EntitySanitizerService sanitizerService
    ) {
        this.loadRepository = loadRepository;
        this.busRepository = busRepository;
        this.sanitizerService = sanitizerService;
    }

    public List<LoadSearchDto> getLoadsByGridId(Long gridId) {
        List<Load> loads = loadRepository.findByGridId(gridId);

        return this.mapLoadsToLoadSearchDtos(loads);
    }

    public List<LoadSearchDto> getLoadsByBusId(Long busId) {
        List<Load> loads = loadRepository.findByBusId(busId);

        return this.mapLoadsToLoadSearchDtos(loads);
    }

    public LoadSearchDto getLoadById(Long id) {
        Load load = loadRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id.toString(), ResourceType.BUS, ResourceIdentifierType.ID));

        return this.mapLoadToLoadSearchDto(load);
    }

    @Transactional
    public LoadSearchDto createLoad(CreateLoadDto loadDto) {
        CreateLoadDto sanitizedDto = sanitizerService.sanitizeCreateLoadDto(loadDto);

        Load load = this.mapCreateLoadDtoToLoad(sanitizedDto);

        Bus bus = busRepository.findById(loadDto.getBusId())
                .orElseThrow(() -> new ResourceNotFoundException(loadDto.getBusId().toString(), ResourceType.BUS, ResourceIdentifierType.ID));
        load.setBus(bus);

        LoadState state = new LoadState();
        state.setGridId(load.getGridId());
        state.setLoad(load);
        load.setState(state);

        Load savedLoad = loadRepository.save(load);

        return this.mapLoadToLoadSearchDto(savedLoad);
    }

    public LoadSearchDto updateLoad(UpdateLoadDto loadDto) {
        UpdateLoadDto sanitizedDto = sanitizerService.sanitizeUpdateLoadDto(loadDto);

        Load existingLoad = loadRepository.findById(sanitizedDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException(sanitizedDto.getId().toString(), ResourceType.BUS, ResourceIdentifierType.ID));

        this.setUpdateLoadDtoToLoad(existingLoad, sanitizedDto);

        Load savedLoad = loadRepository.save(existingLoad);

        return this.mapLoadToLoadSearchDto(savedLoad);
    }

    public void deleteLoad(Long id) {
        Load existingLoad = loadRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id.toString(), ResourceType.BUS, ResourceIdentifierType.ID));

        loadRepository.delete(existingLoad);
    }

    public List<LoadSearchDto> mapLoadsToLoadSearchDtos(List<Load> loads) {
        if (loads == null) return null;

        return loads.stream().map(load -> {
            LoadSearchDto loadDto = this.mapLoadToLoadSearchDto(load);
            LoadStateDto stateDto = this.mapStateToStateSearchDto(load.getState());
            loadDto.setState(stateDto);
            return loadDto;
        }).toList();
    }

    private LoadSearchDto mapLoadToLoadSearchDto(Load load) {
        return LoadMapper.INSTANCE.loadToLoadSearchDto(load);
    }

    private Load mapCreateLoadDtoToLoad(CreateLoadDto loadDto) {
        return LoadMapper.INSTANCE.createLoadDtoToLoad(loadDto);
    }

    private void setUpdateLoadDtoToLoad(Load load, UpdateLoadDto loadDto) {
        load.setLoadName(loadDto.getLoadName());
        load.setLoadType(loadDto.getLoadType());
    }

    private LoadStateDto mapStateToStateSearchDto(LoadState state) {
        return LoadMapper.INSTANCE.stateToStateSearchDto(state);
    }
}
