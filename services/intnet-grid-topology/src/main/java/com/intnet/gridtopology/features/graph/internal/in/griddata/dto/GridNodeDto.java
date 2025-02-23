package com.intnet.gridtopology.features.graph.internal.in.griddata.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GridNodeDto {

    private Long id;
    private Long gridId;
    private String nodeName;
}
