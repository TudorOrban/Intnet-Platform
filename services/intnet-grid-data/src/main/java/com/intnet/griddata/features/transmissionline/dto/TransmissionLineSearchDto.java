package com.intnet.griddata.features.transmissionline.dto;

import com.intnet.griddata.shared.enums.LineType;
import com.intnet.griddata.shared.enums.NodeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransmissionLineSearchDto {

    private Long id;
    private Long gridId;
    private Long sourceNodeId;
    private NodeType sourceNodeType;
    private Long destinationNodeId;
    private NodeType destinationNodeType;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    private LineType lineType;
    private Double length;
    private Double impedance;
    private Double admittance;
    private Double capacity;

    private TransmissionLineStateDto state;
}
