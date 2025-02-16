package com.intnet.network.features.graph.repository;

import com.intnet.network.features.graph.model.Edge;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EdgeRepository extends Neo4jRepository<Edge, Long> {
    Optional<Edge> findById(Long id);
}
