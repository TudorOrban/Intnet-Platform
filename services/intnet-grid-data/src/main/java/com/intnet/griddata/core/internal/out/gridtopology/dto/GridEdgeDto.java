package com.intnet.griddata.core.internal.out.gridtopology.dto;

import com.intnet.griddata.shared.enums.EdgeType;
import com.intnet.griddata.shared.enums.NodeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GridEdgeDto {

    private Long id;
    private Long gridId;
    private Long sourceNodeId;
    private NodeType sourceNodeType;
    private Long destinationNodeId;
    private NodeType destinationNodeType;
    private EdgeType edgeType;
}
