package com.intnet.gridtopology.features.graph.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GridEdge {

    @Id
    private Long id;
    private Long gridId;
    private Long sourceNodeId;
    private NodeType sourceNodeType;
    private Long destinationNodeId;
    private NodeType destinationNodeType;
    private EdgeType edgeType;
}
