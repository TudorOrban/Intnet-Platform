package com.intnet.network.features.graph.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Node
public class GridNode {
    @Id
    @GeneratedValue
    private Long id;

    private Long graphId;

    private NodeType nodeType;
    private Map<String, Object> properties;
}
