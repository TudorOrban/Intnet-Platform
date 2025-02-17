package com.intnet.gridtopology.features.graph.repository;

import com.intnet.gridtopology.features.graph.model.GridEdge;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EdgeRepository extends Neo4jRepository<GridEdge, Long> {
    Optional<GridEdge> findById(Long id);
}
