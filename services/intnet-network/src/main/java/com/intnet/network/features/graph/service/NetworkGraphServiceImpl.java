package com.intnet.network.features.graph.service;

import com.intnet.network.features.graph.model.Edge;
import com.intnet.network.features.graph.model.GridGraph;
import com.intnet.network.features.graph.model.GridNode;
import com.intnet.network.features.graph.repository.EdgeRepository;
import com.intnet.network.features.graph.repository.GridNodeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;

@Service
public class NetworkGraphServiceImpl implements NetworkGraphService {

    private final GridNodeRepository nodeRepository;
    private final EdgeRepository edgeRepository;

    public NetworkGraphServiceImpl(
            GridNodeRepository nodeRepository,
            EdgeRepository edgeRepository
    ) {
        this.nodeRepository = nodeRepository;
        this.edgeRepository = edgeRepository;
    }

    @Transactional
    public void saveGraphToNeo4j(GridGraph graph, Long graphId) {
        for (GridNode node : graph.getNodes().values()) {
            node.getProperties().put("graphId", graphId);
            nodeRepository.save(node);
        }

        for (Edge edge : graph.getEdges().values()) {
            edge.getProperties().put("graphId", graphId);
            edgeRepository.save(edge);
        }
    }

    public GridGraph loadGraphFromNeo4j(Long graphId, Boolean includeAdjacencyMap) {
        GridGraph graph = new GridGraph();

        Iterable<GridNode> nodes = nodeRepository.findAll();
        for (GridNode node : nodes) {
            if (node.getProperties().containsKey("graphId") && node.getProperties().get("graphId").equals(graphId)) {
                graph.addNode(node);
            }
        }

        Iterable<Edge> edges = edgeRepository.findAll();
        for (Edge edge : edges) {
            if (edge.getProperties().containsKey("graphId") && edge.getProperties().get("graphId").equals(graphId)) {
                graph.addEdge(edge);
            }
        }

        if (!includeAdjacencyMap) {
            return graph;
        }

        // Reconstruct adjacency map
        for (GridNode node : graph.getNodes().values()) {
            for (Edge edge : graph.getEdges().values()) {
                boolean isNodeEdge = Objects.equals(edge.getSourceId(), node.getId()) &&
                        Objects.equals(edge.getDestinationId(), node.getId());
                if (isNodeEdge) {
                    graph.getAdjacencyMap().computeIfAbsent(node.getId(), k -> new HashSet<>()).add(edge);
                }
            }
        }

        return graph;
    }
}
