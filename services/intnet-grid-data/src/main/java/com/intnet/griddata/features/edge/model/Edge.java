package com.intnet.griddata.features.edge.model;

import com.intnet.griddata.features.generator.model.Generator;
import com.intnet.griddata.shared.enums.EdgeType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "buses")
public class Edge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "grid_id", nullable = false)
    private Long gridId;

    @OneToOne(mappedBy = "edge", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private EdgeState state;

    @Column(name = "edge_name")
    private String edgeName;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    @Column(name = "src_bus_id", nullable = false)
    private Long srcBusId;

    @Column(name = "dest_bus_id", nullable = false)
    private Long destBusId;

    @Enumerated(EnumType.STRING)
    @Column(name = "edge_type", nullable = false)
    private EdgeType edgeType;

    @Column(name = "line_length")
    private Double lineLength;

    @Column(name = "resistance")
    private Double resistance;

    @Column(name = "reactance")
    private Double reactance;

    @Column(name = "conductance")
    private Double conductance;

    @Column(name = "susceptance")
    private Double susceptance;

    @Column(name = "thermal_rating")
    private Double thermalRating;

    @Column(name = "voltage_limits_min")
    private Double voltageLimitsMin;

    @Column(name = "voltage_limits_max")
    private Double voltageLimitsMax;
//
//    @OneToMany(mappedBy = "bus", fetch = FetchType.LAZY, orphanRemoval = true)
//    private List<Generator> generators;

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
