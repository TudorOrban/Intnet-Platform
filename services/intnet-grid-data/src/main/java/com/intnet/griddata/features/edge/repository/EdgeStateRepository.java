package com.intnet.griddata.features.edge.repository;

import com.intnet.griddata.features.edge.model.EdgeState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EdgeStateRepository extends JpaRepository<EdgeState, Long> {

    @Query("SELECT es FROM EdgeState es WHERE es.edge.id = :edgeId")
    Optional<EdgeState> findByEdgeId(Long edgeId);
}
