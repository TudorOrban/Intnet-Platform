package com.intnet.gridtopology.features.graph.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Node
public class GridNode {

    @Id
    private Long id;
    private Long gridId;
    private String nodeName;
}
