package com.intnet.network.features.graph.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GridNode implements Node {
    private int id;
    private NodeType nodeType;
    private Map<String, Object> properties;
}
