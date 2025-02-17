package com.intnet.griddata.features.bus.repository;

import com.intnet.griddata.features.bus.model.BusState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BusStateRepository extends JpaRepository<BusState, Long> {

    Optional<BusState> findByBusId(Long busId);
}
