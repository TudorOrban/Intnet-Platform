package com.intnet.griddata.features.load.repository;

import com.intnet.griddata.features.load.model.Load;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LoadRepository extends JpaRepository<Load, Long> {

    List<Load> findByGridId(Long gridId);

    @Query("SELECT l FROM Load l WHERE l.bus.id = :busId")
    List<Load> findByBusId(Long busId);
}
