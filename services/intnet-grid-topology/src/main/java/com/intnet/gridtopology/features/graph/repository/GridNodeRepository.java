package com.intnet.gridtopology.features.graph.repository;

import com.intnet.gridtopology.features.graph.model.GridNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GridNodeRepository extends Neo4jRepository<GridNode, Long> {
    Optional<GridNode> findById(Long id);
}
