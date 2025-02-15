package com.intnet.network.features.graph.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GridGraph {

    private Map<Integer, Node> nodes;
    private Map<Integer, Set<Edge>> adjacencyMap;
    private Map<Integer, Edge> edges = new HashMap<>();

    public void addNode(Node node) {
        nodes.put(node.getId(), node);
        adjacencyMap.put(node.getId(), new HashSet<>()); // Initialize edge set
    }

    public Node getNode(int id) {
        return nodes.get(id);
    }

    public void addEdge(Edge edge) {
        edges.put(edge.getId(), edge);
        adjacencyMap.get(edge.getSourceId()).add(edge);
        adjacencyMap.get(edge.getDestinationId()).add(edge);
    }

    public Set<Edge> getConnectedEdges(int nodeId) {
        return adjacencyMap.getOrDefault(nodeId, new HashSet<>());
    }

    public Set<Edge> getAllEdges() {
        return new HashSet<>(edges.values());
    }

    public Set<Node> getNeighbors(int nodeId) {
        Set<Node> neighbors = new HashSet<>();

        for (Edge edge : this.getConnectedEdges(nodeId)) {
            if (edge.getSourceId() == nodeId) {
                neighbors.add(getNode(edge.getDestinationId()));
            } else {
                neighbors.add(getNode(edge.getSourceId()));
            }
        }
        return neighbors;
    }
}
