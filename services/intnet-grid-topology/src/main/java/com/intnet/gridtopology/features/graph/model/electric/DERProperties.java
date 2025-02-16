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
public class DERProperties extends NodeProperties {
    private Double powerOutput;
    private DERType derType;
    private Double voltage;
    private Double reactivePower;
}
