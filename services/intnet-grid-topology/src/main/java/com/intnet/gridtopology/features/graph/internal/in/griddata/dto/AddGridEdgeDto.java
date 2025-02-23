package com.intnet.gridtopology.features.graph.internal.in.griddata.dto;

import com.intnet.gridtopology.features.graph.model.EdgeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddGridEdgeDto {

    private Long id;
    private Long gridId;
    private String edgeName;
    private Long srcNodeId;
    private Long destNodeId;
    private EdgeType edgeType;
}
