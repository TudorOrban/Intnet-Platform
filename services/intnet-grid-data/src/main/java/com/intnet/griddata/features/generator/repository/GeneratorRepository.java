package com.intnet.griddata.features.generator.repository;

import com.intnet.griddata.features.generator.model.Generator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GeneratorRepository extends JpaRepository<Generator, Long> {

    List<Generator> findByGridId(Long gridId);
}
