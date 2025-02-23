package com.intnet.griddata.features.bus.service;

import com.intnet.griddata.core.internal.out.gridtopology.service.GridGraphUpdaterService;
import com.intnet.griddata.features.bus.dto.*;
import com.intnet.griddata.features.bus.model.Bus;
import com.intnet.griddata.features.bus.model.BusState;
import com.intnet.griddata.features.bus.repository.BusRepository;
import com.intnet.griddata.features.generator.dto.GeneratorSearchDto;
import com.intnet.griddata.features.generator.model.Generator;
import com.intnet.griddata.features.generator.service.GeneratorService;
import com.intnet.griddata.shared.exception.types.ResourceIdentifierType;
import com.intnet.griddata.shared.exception.types.ResourceNotFoundException;
import com.intnet.griddata.shared.exception.types.ResourceType;
import com.intnet.griddata.shared.sanitization.service.EntitySanitizerService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusServiceImpl implements BusService {

    private final BusRepository busRepository;
    private final GeneratorService generatorService;
    private final GridGraphUpdaterService graphUpdaterService;
    private final EntitySanitizerService sanitizerService;

    @Autowired
    public BusServiceImpl(
            BusRepository busRepository,
            GeneratorService generatorService,
            GridGraphUpdaterService graphUpdaterService,
            EntitySanitizerService sanitizerService
    ) {
        this.busRepository = busRepository;
        this.generatorService = generatorService;
        this.graphUpdaterService = graphUpdaterService;
        this.sanitizerService = sanitizerService;
    }

    public List<BusSearchDto> getBusesByGridId(Long gridId, Boolean attachComponents) {
        List<Bus> buses;

        if (attachComponents != null && attachComponents) {
            buses = busRepository.findByGridIdWithComponents(gridId);
        } else {
            buses = busRepository.findByGridId(gridId);
        }

        return buses.stream().map(bus -> mapBusToBusSearchDto(bus, attachComponents)).toList();
    }

    public BusSearchDto getBusById(Long id) {
        Bus bus = busRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id.toString(), ResourceType.BUS, ResourceIdentifierType.ID));

        return this.mapBusToBusSearchDto(bus, false);
    }

    @Transactional
    public BusSearchDto createBus(CreateBusDto busDto) {
        CreateBusDto sanitizedDto = sanitizerService.sanitizeCreateBusDto(busDto);

        Bus bus = this.mapCreateBusDtoToBus(sanitizedDto);

        BusState busState = new BusState();
        busState.setGridId(bus.getGridId());
        busState.setBus(bus);
        bus.setState(busState);

        Bus savedBus = busRepository.save(bus);

//        graphUpdaterService.updateGraph(savedBus);

        return this.mapBusToBusSearchDto(savedBus, false);
    }

    public BusSearchDto updateBus(UpdateBusDto busDto) {
        UpdateBusDto sanitizedDto = sanitizerService.sanitizeUpdateBusDto(busDto);

        Bus bus = busRepository.findById(sanitizedDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException(sanitizedDto.getId().toString(), ResourceType.BUS, ResourceIdentifierType.ID));

        this.setUpdateBusDtoToBus(bus, sanitizedDto);

        Bus savedBus = busRepository.save(bus);

        return this.mapBusToBusSearchDto(savedBus, false);
    }

    public void deleteBus(Long id) {
        Bus bus = busRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id.toString(), ResourceType.BUS, ResourceIdentifierType.ID));

        this.busRepository.delete(bus);
    }

    private BusSearchDto mapBusToBusSearchDto(Bus bus, Boolean attachComponents) {
        BusSearchDto busDto = BusMapper.INSTANCE.busToBusSearchDto(bus);
        busDto.setState(this.mapBusStateToBusStateDto(bus.getState()));

        if (attachComponents != null && attachComponents) {
            busDto.setGenerators(generatorService.mapGeneratorsToGeneratorSearchDtos(bus.getGenerators()));
        }

        return busDto;
    }

    private Bus mapCreateBusDtoToBus(CreateBusDto busDto) {
        return BusMapper.INSTANCE.createBusDtoToBus(busDto);
    }

    private BusStateDto mapBusStateToBusStateDto(BusState state) {
        return BusMapper.INSTANCE.busStateToBusStateDto(state);
    }

    private void setUpdateBusDtoToBus(Bus bus, UpdateBusDto busDto) {
        bus.setBusName(busDto.getBusName());
        bus.setLatitude(busDto.getLatitude());
        bus.setLongitude(busDto.getLongitude());
    }
}
