package com.intnet.griddata.features.generator.repository;

import com.intnet.griddata.features.generator.model.Generator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GeneratorRepository extends JpaRepository<Generator, Long> {

    List<Generator> findByGridId(Long gridId);

    @Query("SELECT g FROM Generator g WHERE g.bus.id = :busId")
    List<Generator> findByBusId(Long busId);
}
