package com.intnet.griddata.features.edge.service;

import com.intnet.griddata.core.internal.out.gridtopology.service.GridGraphUpdaterService;
import com.intnet.griddata.features.bus.repository.BusRepository;
import com.intnet.griddata.features.edge.dto.*;
import com.intnet.griddata.features.edge.model.Edge;
import com.intnet.griddata.features.edge.model.EdgeState;
import com.intnet.griddata.features.edge.repository.EdgeRepository;
import com.intnet.griddata.shared.exception.types.ResourceIdentifierType;
import com.intnet.griddata.shared.exception.types.ResourceNotFoundException;
import com.intnet.griddata.shared.exception.types.ResourceType;
import com.intnet.griddata.shared.exception.types.ValidationException;
import com.intnet.griddata.shared.sanitization.service.EntitySanitizerService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * Service responsible for CRUD ops for the Edge feature
 */
@Service
public class EdgeServiceImpl implements EdgeService {

    private final EdgeRepository edgeRepository;
    private final BusRepository busRepository;
    private final GridGraphUpdaterService graphUpdaterService;
    private final EntitySanitizerService sanitizerService;

    @Autowired
    public EdgeServiceImpl(
            EdgeRepository edgeRepository,
            BusRepository busRepository,
            GridGraphUpdaterService graphUpdaterService,
            EntitySanitizerService sanitizerService
    ) {
        this.edgeRepository = edgeRepository;
        this.busRepository = busRepository;
        this.graphUpdaterService = graphUpdaterService;
        this.sanitizerService = sanitizerService;
    }

    public List<EdgeSearchDto> getEdgesByGridId(Long gridId, Boolean attachComponents) {
        List<Edge> edges;

        if (attachComponents != null && attachComponents) {
            edges = edgeRepository.findByGridIdWithComponents(gridId);
        } else {
            edges = edgeRepository.findByGridId(gridId);
        }

        return edges.stream().map(edge -> this.mapEdgeToEdgeSearchDto(edge, attachComponents)).toList();
    }

    public EdgeSearchDto getEdgeById(Long id) {
        Edge edge = edgeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id.toString(), ResourceType.EDGE, ResourceIdentifierType.ID));

        return this.mapEdgeToEdgeSearchDto(edge, false);
    }

    @Transactional
    public EdgeSearchDto createEdge(CreateEdgeDto edgeDto) {
        CreateEdgeDto sanitizedDto = sanitizerService.sanitizeCreateEdgeDto(edgeDto);

        if (!busRepository.existsById(edgeDto.getSrcBusId()) || !busRepository.existsById(edgeDto.getDestBusId())) {
            throw new ValidationException("Error creating edge: Invalid Source or Bus ID");
        }

        Edge edge = this.mapCreateEdgeDtoToEdge(sanitizedDto);

        EdgeState edgeState = new EdgeState();
        edgeState.setGridId(edge.getGridId());
        edgeState.setEdge(edge);
        edge.setState(edgeState);

        Edge savedEdge = edgeRepository.save(edge);

//        graphUpdaterService.updateGraph(savedEdge);

        return this.mapEdgeToEdgeSearchDto(savedEdge, false);
    }

    public EdgeSearchDto updateEdge(UpdateEdgeDto edgeDto) {
        UpdateEdgeDto sanitizedDto = sanitizerService.sanitizeUpdateEdgeDto(edgeDto);

        Edge edge = edgeRepository.findById(sanitizedDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException(sanitizedDto.getId().toString(), ResourceType.EDGE, ResourceIdentifierType.ID));

        this.setUpdateEdgeDtoToEdge(edge, sanitizedDto);

        Edge savedEdge = edgeRepository.save(edge);

        return this.mapEdgeToEdgeSearchDto(savedEdge, false);
    }

    public void deleteEdge(Long id) {
        Edge edge = edgeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id.toString(), ResourceType.EDGE, ResourceIdentifierType.ID));

        this.edgeRepository.delete(edge);
    }

    private EdgeSearchDto mapEdgeToEdgeSearchDto(Edge edge, Boolean attachComponents) {
        EdgeSearchDto edgeDto = EdgeMapper.INSTANCE.edgeToEdgeSearchDto(edge);
        edgeDto.setState(this.mapEdgeStateToEdgeStateDto(edge.getState()));

        if (attachComponents != null && attachComponents) {
//            edgeDto.setGenerators(generatorService.mapGeneratorsToGeneratorSearchDtos(edge.getGenerators()));
        }

        return edgeDto;
    }

    private Edge mapCreateEdgeDtoToEdge(CreateEdgeDto edgeDto) {
        return EdgeMapper.INSTANCE.createEdgeDtoToEdge(edgeDto);
    }

    private EdgeStateDto mapEdgeStateToEdgeStateDto(EdgeState state) {
        return EdgeMapper.INSTANCE.edgeStateToEdgeStateDto(state);
    }

    private void setUpdateEdgeDtoToEdge(Edge edge, UpdateEdgeDto edgeDto) {
        edge.setEdgeName(edgeDto.getEdgeName());
        edge.setLineLength(edgeDto.getLineLength());
        edge.setResistance(edgeDto.getResistance());
        edge.setReactance(edgeDto.getReactance());
        edge.setConductance(edgeDto.getConductance());
        edge.setSusceptance(edgeDto.getSusceptance());
        edge.setThermalRating(edgeDto.getThermalRating());
        edge.setVoltageLimitsMin(edgeDto.getVoltageLimitsMin());
        edge.setVoltageLimitsMax(edgeDto.getVoltageLimitsMax());
    }
}
