package com.intnet.griddata.features.generator.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "generator_states")
public class GeneratorState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "grid_id", nullable = false)
    private Long gridId;

    @OneToOne
    @JoinColumn(name = "generator_id", nullable = false, insertable = false, updatable = false)
    private Generator generator;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    @Column(name = "active_power_generation")
    private Double activePowerGeneration;

    @Column(name = "reactive_power_generation")
    private Double reactivePowerGeneration;

    @Column(name = "generator_voltage_setpoint")
    private Double generatorVoltageSetpoint;

    @PreUpdate
    private void onUpdate() {
        updatedAt = ZonedDateTime.now();
    }
}
