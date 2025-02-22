package com.intnet.griddata.features.bus.service;

import com.intnet.griddata.core.internal.out.gridtopology.service.GridGraphUpdaterService;
import com.intnet.griddata.features.bus.dto.*;
import com.intnet.griddata.features.bus.model.Bus;
import com.intnet.griddata.features.bus.model.BusState;
import com.intnet.griddata.features.bus.repository.BusRepository;
import com.intnet.griddata.features.bus.repository.BusStateRepository;
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
    private final BusStateRepository busStateRepository;
    private final GridGraphUpdaterService graphUpdaterService;
    private final EntitySanitizerService sanitizerService;

    @Autowired
    public BusServiceImpl(
            BusRepository busRepository,
            BusStateRepository busStateRepository,
            GridGraphUpdaterService graphUpdaterService,
            EntitySanitizerService sanitizerService
    ) {
        this.busRepository = busRepository;
        this.busStateRepository = busStateRepository;
        this.graphUpdaterService = graphUpdaterService;
        this.sanitizerService = sanitizerService;
    }

    public List<BusSearchDto> getBusesByGridId(Long gridId) {
        List<Bus> buses = busRepository.findByGridId(gridId);

        return buses.stream().map(this::mapBusToBusSearchDto).toList();
    }

    public BusSearchDto getBusById(Long id) {
        Bus bus = busRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id.toString(), ResourceType.BUS, ResourceIdentifierType.ID));

        return this.mapBusToBusSearchDto(bus);
    }

    @Transactional
    public BusSearchDto createBus(CreateBusDto busDto) {
        CreateBusDto sanitizedBusDto = sanitizerService.sanitizeCreateBusDto(busDto);

        Bus bus = this.mapCreateBusDtoToBus(sanitizedBusDto);

        BusState busState = new BusState();
        busState.setGridId(bus.getGridId());
        bus.setState(busState);

        Bus savedBus = busRepository.save(bus);

        graphUpdaterService.updateGraph(savedBus);

        return this.mapBusToBusSearchDto(savedBus);
    }

    public BusSearchDto updateBus(UpdateBusDto busDto) {
        UpdateBusDto sanitizedBusDto = sanitizerService.sanitizeUpdateBusDto(busDto);

        Bus bus = busRepository.findById(sanitizedBusDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException(sanitizedBusDto.getId().toString(), ResourceType.BUS, ResourceIdentifierType.ID));

        this.setUpdateBusDtoToBus(bus, sanitizedBusDto);

        Bus savedBus = busRepository.save(bus);

        return this.mapBusToBusSearchDto(savedBus);
    }

    public void deleteBus(Long id) {
        Bus bus = busRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id.toString(), ResourceType.BUS, ResourceIdentifierType.ID));

        busStateRepository.deleteByBusId(id);

        this.busRepository.delete(bus);
    }

    private BusSearchDto mapBusToBusSearchDto(Bus bus) {
        BusSearchDto busDto = BusMapper.INSTANCE.busToBusSearchDto(bus);
        busDto.setState(this.mapBusStateToBusStateDto(bus.getState()));
        return busDto;
    }

    private Bus mapCreateBusDtoToBus(CreateBusDto busDto) {
        return BusMapper.INSTANCE.createBusDtoToBus(busDto);
    }

    private BusStateDto mapBusStateToBusStateDto(BusState state) {
        return BusMapper.INSTANCE.busStateToBusStateDto(state);
    }

    private void setUpdateBusDtoToBus(Bus bus, UpdateBusDto busDto) {
        bus.setLatitude(busDto.getLatitude());
        bus.setLongitude(busDto.getLongitude());
    }
}
