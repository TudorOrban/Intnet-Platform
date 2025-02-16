package com.intnet.network.features.graph.model.electric;

import com.intnet.network.features.graph.model.EdgeProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransmissionLineProperties extends EdgeProperties {
    private Double admittance;
}
