package com.intnet.griddevicemanager.features.device.dto;

import com.intnet.griddevicemanager.features.device.model.GridElementType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAssociationDto {

    private Long deviceId;
    private Long gridElementId;
    private GridElementType elementType;
}
