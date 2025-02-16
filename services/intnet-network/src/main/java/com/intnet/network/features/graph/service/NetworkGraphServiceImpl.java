package com.intnet.network.features.graph.service;

import com.intnet.network.features.graph.dto.CreateGridGraphDto;
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
    public void createGraph(CreateGridGraphDto graphDto) {
        GridGraph graph = graphDto.getGraph();
        Long graphId = graphDto.getGraphId();

        for (GridNode node : graph.getNodes().values()) {
            node.setGraphId(graphId);
            nodeRepository.save(node);
        }

        for (Edge edge : graph.getEdges().values()) {
            edge.setGraphId(graphId);
            edgeRepository.save(edge);
        }
    }

    public GridGraph loadGraphFromNeo4j(Long graphId, Boolean includeAdjacencyMap) {
        GridGraph graph = new GridGraph();

        Iterable<GridNode> nodes = nodeRepository.findAll();
        for (GridNode node : nodes) {
            if (node.getGraphId().equals(graphId)) {
                graph.addNode(node);
            }
        }

        Iterable<Edge> edges = edgeRepository.findAll();
        for (Edge edge : edges) {
            if (edge.getGraphId().equals(graphId)) {
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
