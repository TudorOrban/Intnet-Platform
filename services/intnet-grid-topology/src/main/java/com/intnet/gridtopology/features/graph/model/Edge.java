package com.intnet.gridtopology.features.graph.model;

import com.intnet.gridtopology.features.graph.model.electric.DistributionLineProperties;
import com.intnet.gridtopology.features.graph.model.electric.TransmissionLineProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;

import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Edge {

    @Id
    @GeneratedValue
    private Long id;

    private Long graphId;

    private Long sourceId;
    private Long destinationId;

    private EdgeType edgeType;
    private TransmissionLineProperties transmissionLineProperties;
    private DistributionLineProperties distributionLineProperties;
    private Map<String, Object> properties;
}
