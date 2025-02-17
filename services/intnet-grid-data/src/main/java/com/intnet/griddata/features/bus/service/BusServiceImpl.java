package com.intnet.griddata.features.bus.service;

import com.intnet.griddata.features.bus.dto.*;
import com.intnet.griddata.features.bus.model.Bus;
import com.intnet.griddata.features.bus.model.BusState;
import com.intnet.griddata.features.bus.repository.BusRepository;
import com.intnet.griddata.features.bus.repository.BusStateRepository;
import com.intnet.griddata.shared.exception.types.ResourceIdentifierType;
import com.intnet.griddata.shared.exception.types.ResourceNotFoundException;
import com.intnet.griddata.shared.exception.types.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusServiceImpl implements BusService {

    private final BusRepository busRepository;
    private final BusStateRepository busStateRepository;

    @Autowired
    public BusServiceImpl(
            BusRepository busRepository,
            BusStateRepository busStateRepository
    ) {
        this.busRepository = busRepository;
        this.busStateRepository = busStateRepository;
    }

    public BusSearchDto getBusById(Long id, Boolean attachState) {
        Bus bus = busRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id.toString(), ResourceType.BUS, ResourceIdentifierType.ID));

        BusStateDto stateDto = null;
        if (attachState) {
            BusState state = busStateRepository.findByBusId(bus.getId())
                    .orElseThrow(() -> new ResourceNotFoundException(bus.getId().toString(), ResourceType.BUS_STATE, ResourceIdentifierType.BUS_ID));
            stateDto = this.mapBusStateToBusStateDto(state);
        }

        return this.mapBusToBusSearchDto(bus, stateDto);
    }

    public BusSearchDto createBus(CreateBusDto busDto) {
        Bus bus = this.mapCreateBusDtoToBus(busDto);

        Bus savedBus = busRepository.save(bus);

        BusStateDto stateDto = this.createBusState(savedBus);

        return this.mapBusToBusSearchDto(savedBus, stateDto);
    }

    private BusStateDto createBusState(Bus savedBus) {
        BusState busState = new BusState();
        busState.setGridId(savedBus.getGridId());
        busState.setBusId(savedBus.getId());

        BusState savedState = busStateRepository.save(busState);
        return this.mapBusStateToBusStateDto(savedState);
    }

    public BusSearchDto updateBus(UpdateBusDto busDto) {
        Bus bus = busRepository.findById(busDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException(busDto.getId().toString(), ResourceType.BUS, ResourceIdentifierType.ID));

        bus.setLatitude(busDto.getLatitude());
        bus.setLongitude(busDto.getLongitude());

        Bus savedBus = busRepository.save(bus);

        return this.mapBusToBusSearchDto(savedBus, null);
    }

    public void deleteBus(Long id) {
        Bus bus = busRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id.toString(), ResourceType.BUS, ResourceIdentifierType.ID));

        this.busRepository.delete(bus);
    }

    private BusSearchDto mapBusToBusSearchDto(Bus bus, BusStateDto stateDto) {
        BusSearchDto busDto = BusMapper.INSTANCE.busToBusSearchDto(bus);
        busDto.setState(stateDto);
        return busDto;
    }

    private Bus mapCreateBusDtoToBus(CreateBusDto busDto) {
        return BusMapper.INSTANCE.createBusDtoToBus(busDto);
    }

    private BusStateDto mapBusStateToBusStateDto(BusState state) {
        return BusMapper.INSTANCE.busStateToBusStateDto(state);
    }
}
