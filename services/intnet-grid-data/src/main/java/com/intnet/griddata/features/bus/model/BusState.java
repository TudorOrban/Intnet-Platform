package com.intnet.griddata.features.bus.model;

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
public class BusState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "grid_id", nullable = false)
    private Long gridId;

    @Column(name = "bus_id", nullable = false)
    private Long busId;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    @Column(name = "voltage_magnitude")
    private Double voltageMagnitude;

    @Column(name = "voltage_angle")
    private Double voltageAngle;

    @Column(name = "active_power_injection")
    private Double activePowerInjection;

    @Column(name = "reactive_power_injection")
    private Double reactivePowerInjection;

    @Column(name = "shunt_capacitor_reactor_status")
    private Boolean shuntCapacitorReactorStatus;

    @Column(name = "phase_shifting_transformer_tap_position")
    private Double phaseShiftingTransformerTapPosition;

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
