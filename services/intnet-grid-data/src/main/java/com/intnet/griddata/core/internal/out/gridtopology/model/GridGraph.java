package com.intnet.griddata.core.internal.out.gridtopology.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
public class GridGraph {

    private Map<Long, GridNode> nodes;
    private Map<Long, GridEdge> edges;

    public GridGraph() {
        nodes = new HashMap<>();
        edges = new HashMap<>();
    }

    public void addNode(GridNode node) {
        nodes.put(node.getId(), node);
    }

    public GridNode getNode(Long id) {
        return nodes.get(id);
    }

    public void addEdge(GridEdge edge) {
        edges.put(edge.getId(), edge);
    }
}
