package com.intnet.griddata.features.bus.repository;

import com.intnet.griddata.features.bus.model.BusState;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BusStateRepository extends JpaRepository<BusState, Long> {

    @Query("SELECT bs FROM BusState bs WHERE bs.bus.id = :busId")
    Optional<BusState> findByBusId(Long busId);

    @Modifying
    @Transactional
    @Query("DELETE FROM BusState bs WHERE bs.bus.id = :busId")
    void deleteByBusId(Long busId);
}
