package com.intnet.griddata.core.internal.out.gridtopology.dto;

import com.intnet.griddata.shared.enums.NodeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GridNodeDto {

    private Long id;
    private Long gridId;
    private NodeType nodeType;
}
