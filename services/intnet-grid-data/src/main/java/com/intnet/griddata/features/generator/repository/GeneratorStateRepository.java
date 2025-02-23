package com.intnet.griddata.features.generator.repository;

import com.intnet.griddata.features.generator.model.GeneratorState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface GeneratorStateRepository extends JpaRepository<GeneratorState, Long> {

    @Query("SELECT gs FROM GeneratorState gs WHERE gs.generator.id = :generatorId")
    Optional<GeneratorState> findByGeneratorId(Long generatorId);
}
