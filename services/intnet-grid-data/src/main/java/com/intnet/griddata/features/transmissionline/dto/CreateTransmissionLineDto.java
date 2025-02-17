package com.intnet.griddata.features.transmissionline.dto;

import com.intnet.griddata.shared.enums.LineType;
import com.intnet.griddata.shared.enums.NodeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTransmissionLineDto {

    private Long gridId;
    private Long sourceNodeId;
    private NodeType sourceNodeType;
    private Long destinationNodeId;
    private NodeType destinationNodeType;

    private LineType lineType;
    private Double length;
    private Double impedance;
    private Double admittance;
    private Double capacity;
}
