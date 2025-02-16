package com.intnet.network.features.graph.model;

import com.intnet.network.shared.location.model.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NodeProperties {
    private Location location;
    private NodeStatus nodeStatus;
}
