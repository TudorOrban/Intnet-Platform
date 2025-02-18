package com.intnet.gridhistory.features.bus.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bus_state_history")
public class BusStateRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "grid_id", nullable = false)
    private Long gridId;

    @Column(name = "bus_id", nullable = false)
    private Long busId;

    @Column(name = "time", nullable = false)
    private ZonedDateTime time;

    private Double voltage;
    private Double load;
    private Double generation;

    @Column(name = "phase_angle")
    private Double phaseAngle;

    @PrePersist
    private void beforePersist() {
        time = ZonedDateTime.now();
    }
}
