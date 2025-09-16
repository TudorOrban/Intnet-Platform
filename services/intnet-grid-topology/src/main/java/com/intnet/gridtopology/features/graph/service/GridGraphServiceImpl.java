package com.intnet.gridtopology.features.graph.service;

import com.intnet.gridtopology.features.graph.internal.in.griddata.dto.*;
import com.intnet.gridtopology.features.graph.model.GridEdge;
import com.intnet.gridtopology.features.graph.model.GridGraph;
import com.intnet.gridtopology.features.graph.model.GridNode;
import com.intnet.gridtopology.features.graph.repository.EdgeRepository;
import com.intnet.gridtopology.features.graph.repository.GridNodeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

/*
 * Service responsible for CRUD ops for the Grid Graph entity
 */
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

    public GridGraph getGraphByGridId(Long graphId) {
        GridGraph graph = new GridGraph();

        Iterable<GridNode> nodes = nodeRepository.findAll();
        for (GridNode node : nodes) {
            if (node.getGridId().equals(graphId)) {
                graph.addNode(node);
            }
        }

        Iterable<GridEdge> edges = edgeRepository.findAll();
        for (GridEdge edge : edges) {
            if (edge.getGridId().equals(graphId)) {
                graph.addEdge(edge);
            }
        }

        return graph;
    }

    public GridNodeDto addNode(AddGridNodeDto nodeDto) {
        GridNode gridNode = this.mapAddGridNodeDtoToGridNode(nodeDto);

        GridNode savedNode = nodeRepository.save(gridNode);

        return this.mapGridNodeToGridNodeDto(savedNode);
    }

    public GridEdgeDto addEdge(AddGridEdgeDto nodeDto) {
        GridEdge gridEdge = this.mapAddGridEdgeDtoToGridEdge(nodeDto);

        GridEdge savedEdge = edgeRepository.save(gridEdge);

        return this.mapGridEdgeToGridEdgeDto(savedEdge);
    }

    @Transactional
    public void createGraph(CreateGridGraphDto graphDto) {
        GridGraph graph = graphDto.getGraph();
        Long gridId = graphDto.getGridId();

        for (GridNode node : graph.getNodes().values()) {
            node.setGridId(gridId);
            nodeRepository.save(node);
        }

        for (GridEdge edge : graph.getEdges().values()) {
            edge.setGridId(gridId);
            edgeRepository.save(edge);
        }
    }

    private GridNodeDto mapGridNodeToGridNodeDto(GridNode node) {
        return GridMapper.INSTANCE.gridNodeToGridNodeDto(node);
    }

    private GridNode mapAddGridNodeDtoToGridNode(AddGridNodeDto nodeDto) {
        return GridMapper.INSTANCE.addGridNodeDtoToGridNode(nodeDto);
    }

    private GridEdgeDto mapGridEdgeToGridEdgeDto(GridEdge node) {
        return GridMapper.INSTANCE.gridEdgeToGridEdgeDto(node);
    }

    private GridEdge mapAddGridEdgeDtoToGridEdge(AddGridEdgeDto nodeDto) {
        return GridMapper.INSTANCE.addGridEdgeDtoToGridEdge(nodeDto);
    }
}
