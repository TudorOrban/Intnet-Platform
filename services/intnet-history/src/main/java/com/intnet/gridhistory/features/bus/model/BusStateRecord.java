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
    private void beforePersist() {
        time = ZonedDateTime.now();
    }
}
