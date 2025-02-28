package com.intnet.griddata.features.load.repository;

import com.intnet.griddata.features.load.model.LoadState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LoadStateRepository extends JpaRepository<LoadState, Long> {

    @Query("SELECT ls FROM LoadState ls WHERE ls.load.id = :loadId")
    Optional<LoadState> findByLoadId(Long loadId);
}
