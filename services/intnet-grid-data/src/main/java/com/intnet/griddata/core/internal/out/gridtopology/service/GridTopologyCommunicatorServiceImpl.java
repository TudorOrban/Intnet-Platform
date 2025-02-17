package com.intnet.griddata.core.internal.out.gridtopology.service;

import com.intnet.griddata.core.internal.out.gridtopology.dto.*;
import com.intnet.griddata.core.internal.out.gridtopology.model.GridGraph;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class GridTopologyCommunicatorServiceImpl implements GridTopologyCommunicatorService {

    private final WebClient webClient;

    @Value("${grid-topology.service.url}")
    private String gridTopologyServiceUrl;

    public GridTopologyCommunicatorServiceImpl(
            WebClient.Builder webClientBuilder
    ) {
        this.webClient = webClientBuilder.baseUrl(gridTopologyServiceUrl + "/grid-graphs").build();
    }

    public GridGraph getGraphByGridId(Long gridId) {
        return webClient.get()
                .uri("/{id}", gridId)
                .retrieve()
                .bodyToMono(GridGraph.class)
                .block();
    }

    public void createGraph(CreateGridGraphDto graphDto) {
        webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(graphDto), CreateGridGraphDto.class)
                .retrieve().toBodilessEntity().block();
    }

    public GridNodeDto addNode(AddGridNodeDto nodeDto) {
        return webClient.post()
                .uri("/node")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(nodeDto), AddGridNodeDto.class)
                .retrieve()
                .bodyToMono(GridNodeDto.class)
                .block();
    }

    public GridEdgeDto addEdge(AddGridEdgeDto edgeDto) {
        return webClient.post()
                .uri("/edge")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(edgeDto), AddGridEdgeDto.class)
                .retrieve()
                .bodyToMono(GridEdgeDto.class)
                .block();
    }

}
