package com.intnet.gridtopology.features.graph.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Edge {

    @Id
    private Long id;
    private Long gridId;
    private Long sourceId;
    private Long destinationId;
    private EdgeType edgeType;
}
