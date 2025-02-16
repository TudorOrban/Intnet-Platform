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
public class LoadProperties extends NodeProperties {
    private Double powerDemand;
    private LoadType loadType;
}

