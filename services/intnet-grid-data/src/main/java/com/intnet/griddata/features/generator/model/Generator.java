package com.intnet.griddata.features.generator.model;

import com.intnet.griddata.features.bus.model.Bus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "generators")
public class Generator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "grid_id", nullable = false)
    private Long gridId;

    @ManyToOne
    @JoinColumn(name = "bus_id", nullable = false)
    private Bus bus;

    @OneToOne(mappedBy = "generator", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private GeneratorState state;

    @Column(name = "generator_name")
    private String generatorName;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    @Column(name = "generator_max_active_power")
    private Double generatorMaxActivePower;

    @Column(name = "generator_min_active_power")
    private Double generatorMinActivePower;

    @Column(name = "generator_max_reactive_power")
    private Double generatorMaxReactivePower;

    @Column(name = "generator_min_reactive_power")
    private Double generatorMinReactivePower;

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
