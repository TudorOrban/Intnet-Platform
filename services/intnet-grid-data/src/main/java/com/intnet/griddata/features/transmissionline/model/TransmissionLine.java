package com.intnet.griddata.features.transmissionline.model;

import com.intnet.griddata.shared.enums.LineType;
import com.intnet.griddata.shared.enums.NodeType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transmission_lines")
public class TransmissionLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "grid_id", nullable = false)
    private Long gridId;

    @Column(name = "source_node_id", nullable = false)
    private Long sourceNodeId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NodeType sourceNodeType;

    @Column(name = "destination_node_id", nullable = false)
    private Long destinationNodeId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NodeType destinationNodeType;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    private Double length; // km
    private Double impedance; // Ω/km
    private Double admittance; // S/km
    private Double capacity; // MVA

    @Enumerated(EnumType.STRING)
    private LineType lineType;

    @PrePersist
    private void onCreate() {
        createdAt = ZonedDateTime.now();
        updatedAt = ZonedDateTime.now();
    }

    @PreUpdate
    private void onUpdate() {
        updatedAt = ZonedDateTime.now();
    }
}
