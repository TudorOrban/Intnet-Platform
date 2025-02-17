package com.intnet.griddata.features.substation.repository;

import com.intnet.griddata.features.substation.model.SubstationState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubstationStateRepository extends JpaRepository<SubstationState, Long> {

    Optional<SubstationState> findBySubstationId(Long substationId);
}
