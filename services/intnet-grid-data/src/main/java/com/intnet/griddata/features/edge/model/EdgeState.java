package com.intnet.griddata.features.edge.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bus_states")
public class EdgeState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "grid_id", nullable = false)
    private Long gridId;

    @OneToOne
    @JoinColumn(name = "edge_id", nullable = false)
    private Edge edge;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    @Column(name = "line_switching_status")
    private Double lineSwitchingStatus;

    @PrePersist
    private void onCreate() {
        updatedAt = ZonedDateTime.now();
    }

    @PreUpdate
    private void onUpdate() {
        updatedAt = ZonedDateTime.now();
    }
}
