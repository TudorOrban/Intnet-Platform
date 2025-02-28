package com.intnet.griddata.features.load.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "load_states")
public class LoadState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "grid_id", nullable = false)
    private Long gridId;

    @OneToOne
    @JoinColumn(name = "load_id", nullable = false)
    private Load load;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    @Column(name = "active_power_load")
    private Double activePowerLoad;

    @Column(name = "reactive_power_load")
    private Double reactivePowerLoad;

    @PreUpdate
    private void onUpdate() {
        updatedAt = ZonedDateTime.now();
    }
}
