package com.intnet.griddata.features.bus.repository;

import com.intnet.griddata.features.bus.model.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BusRepository extends JpaRepository<Bus, Long> {

    List<Bus> findByGridId(Long gridId);

    @Query("SELECT b FROM Bus b " +
            "LEFT JOIN FETCH b.generators " +
//            "LEFT JOIN FETCH b.loads " +
            "WHERE b.gridId = :gridId")
    List<Bus> findByGridIdWithComponents(@Param("gridId") Long gridId);
}
