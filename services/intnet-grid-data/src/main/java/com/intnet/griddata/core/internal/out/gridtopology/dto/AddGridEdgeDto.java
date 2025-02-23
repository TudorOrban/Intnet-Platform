package com.intnet.griddata.core.internal.out.gridtopology.dto;


import com.intnet.griddata.shared.enums.EdgeType;
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
