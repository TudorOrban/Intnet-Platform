package com.intnet.griddata.core.internal.out.gridtopology.dto;

import com.intnet.griddata.shared.enums.NodeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddGridNodeDto {

    private Long id;
    private Long gridId;
    private String nodeName;
}
