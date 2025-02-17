package com.intnet.gridtopology.features.graph.service;

import com.intnet.gridtopology.features.graph.internal.in.griddata.dto.CreateGridGraphDto;
import com.intnet.gridtopology.features.graph.model.Edge;
import com.intnet.gridtopology.features.graph.model.GridGraph;
import com.intnet.gridtopology.features.graph.model.GridNode;
import com.intnet.gridtopology.features.graph.repository.EdgeRepository;
import com.intnet.gridtopology.features.graph.repository.GridNodeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;

@Service
public class GridGraphServiceImpl implements GridGraphService {

    private final GridNodeRepository nodeRepository;
    private final EdgeRepository edgeRepository;

    public GridGraphServiceImpl(
            GridNodeRepository nodeRepository,
            EdgeRepository edgeRepository
    ) {
        this.nodeRepository = nodeRepository;
        this.edgeRepository = edgeRepository;
    }

    @Transactional
    public void createGraph(CreateGridGraphDto graphDto) {
        GridGraph graph = graphDto.getGraph();
        Long gridId = graphDto.getGridId();

        for (GridNode node : graph.getNodes().values()) {
            node.setGridId(gridId);
            nodeRepository.save(node);
        }

        for (Edge edge : graph.getEdges().values()) {
            edge.setGridId(gridId);
            edgeRepository.save(edge);
        }
    }

    public GridGraph getGraphByGridId(Long graphId, Boolean includeAdjacencyMap) {
        GridGraph graph = new GridGraph();

        Iterable<GridNode> nodes = nodeRepository.findAll();
        for (GridNode node : nodes) {
            if (node.getGridId().equals(graphId)) {
                graph.addNode(node);
            }
        }

        Iterable<Edge> edges = edgeRepository.findAll();
        for (Edge edge : edges) {
            if (edge.getGridId().equals(graphId)) {
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
