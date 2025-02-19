package com.intnet.griddevicemanager.features.device.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "grid_element_associations")
public class GridElementAssociation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "device_id", nullable = false)
    private Long deviceId;

    @Column(name = "grid_element_id", nullable = false)
    private Long gridElementId;

    @Enumerated(EnumType.STRING)
    @Column(name = "grid_element_type", nullable = false)
    private GridElementType elementType;
}
