package com.intnet.network.features.graph.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Edge {
    private int id;
    private int sourceId;
    private int destinationId;
    private Map<String, Object> properties;
}
