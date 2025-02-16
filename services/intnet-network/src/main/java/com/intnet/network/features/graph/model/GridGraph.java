package com.intnet.network.features.graph.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;

@Data
@AllArgsConstructor
public class GridGraph {

    private Map<Long, GridNode> nodes;
    private Map<Long, Set<Edge>> adjacencyMap;
    private Map<Long, Edge> edges;

    public GridGraph() {
        nodes = new HashMap<>();
        adjacencyMap = new HashMap<>();
        edges = new HashMap<>();
    }

    public void addNode(GridNode node) {
        nodes.put(node.getId(), node);
        adjacencyMap.put(node.getId(), new HashSet<>());
    }

    public GridNode getNode(Long id) {
        return nodes.get(id);
    }

    public void addEdge(Edge edge) {
        edges.put(edge.getId(), edge);
        adjacencyMap.get(edge.getSourceId()).add(edge);
        adjacencyMap.get(edge.getDestinationId()).add(edge);
    }

    public Set<Edge> getConnectedEdges(Long nodeId) {
        return adjacencyMap.getOrDefault(nodeId, new HashSet<>());
    }

    public Set<Edge> getAllEdges() {
        return new HashSet<>(edges.values());
    }

    public Set<GridNode> getNeighbors(Long nodeId) {
        Set<GridNode> neighbors = new HashSet<>();

        for (Edge edge : this.getConnectedEdges(nodeId)) {
            if (Objects.equals(edge.getSourceId(), nodeId)) {
                neighbors.add(getNode(edge.getDestinationId()));
            } else {
                neighbors.add(getNode(edge.getSourceId()));
            }
        }
        return neighbors;
    }
}
