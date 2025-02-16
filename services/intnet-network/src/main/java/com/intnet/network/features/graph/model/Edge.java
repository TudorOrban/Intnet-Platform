package com.intnet.network.features.graph.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;

import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Edge {
    @Id
    @GeneratedValue
    private Long id;

    private Long sourceId;
    private Long destinationId;
    private Map<String, Object> properties;
}
