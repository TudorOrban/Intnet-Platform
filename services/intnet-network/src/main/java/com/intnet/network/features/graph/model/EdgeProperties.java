package com.intnet.network.features.graph.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EdgeProperties {
    private Double length;
    private Double impedance;
    private Double capacity;
}
