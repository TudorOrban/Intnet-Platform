package com.intnet.griddata.features.substation.repository;

import com.intnet.griddata.features.substation.model.SubstationState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubstationStateRepository extends JpaRepository<SubstationState, Long> {
}
