package com.intnet.griddata.features.transmissionline.dto;

import com.intnet.griddata.shared.enums.LineType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTransmissionLineDto {

    private Long id;
    private Long gridId;
    private LineType lineType;
    private Double length;
    private Double impedance;
    private Double admittance;
    private Double capacity;
}
