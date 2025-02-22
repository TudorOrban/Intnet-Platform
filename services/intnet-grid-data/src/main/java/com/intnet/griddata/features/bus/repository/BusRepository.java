package com.intnet.griddata.features.bus.repository;

import com.intnet.griddata.features.bus.model.Bus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusRepository extends JpaRepository<Bus, Long> {

    List<Bus> findByGridId(Long gridId);
}
