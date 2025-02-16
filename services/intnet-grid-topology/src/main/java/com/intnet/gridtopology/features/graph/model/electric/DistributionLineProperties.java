package com.intnet.gridtopology.features.graph.model.electric;

import com.intnet.gridtopology.features.graph.model.EdgeProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DistributionLineProperties extends EdgeProperties {
    private String temporaryLabel;
}
