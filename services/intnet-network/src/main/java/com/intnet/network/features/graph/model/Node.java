package com.intnet.network.features.graph.model;

import java.util.Map;

public interface Node {
    int getId();
    NodeType getNodeType();
    Map<String, Object> getProperties();
}
