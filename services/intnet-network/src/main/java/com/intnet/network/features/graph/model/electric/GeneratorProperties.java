package com.intnet.network.features.graph.model.electric;

import com.intnet.network.features.graph.model.NodeProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneratorProperties extends NodeProperties {
    private Double powerOutput;
    private Double voltage;
    private FuelType fuelType;
    private Double reactivePower;
}
