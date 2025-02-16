package com.intnet.gridtopology.features.graph.model.electric;

import com.intnet.gridtopology.features.graph.model.NodeProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransformerProperties extends NodeProperties {
    private Double capacity;
    private String voltageRatio;
    private Double impedance;
    private Integer tapPosition;
}
