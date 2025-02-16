package com.intnet.network.features.graph.model.electric;

import com.intnet.network.features.graph.model.NodeProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubstationProperties extends NodeProperties {
    private Double voltageLevel;
    private Double capacity;
    private Integer transformers;
    private List<Long> connectedBuses;
    private String status;
}
