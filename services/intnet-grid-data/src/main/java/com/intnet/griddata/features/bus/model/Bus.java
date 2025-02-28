package com.intnet.griddata.features.bus.model;

import com.intnet.griddata.features.generator.model.Generator;
import com.intnet.griddata.features.load.model.Load;
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
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "grid_id", nullable = false)
    private Long gridId;

    @OneToOne(mappedBy = "bus", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private BusState state;

    @Column(name = "bus_name")
    private String busName;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @OneToMany(mappedBy = "bus", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Generator> generators;

    @OneToMany(mappedBy = "bus", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Load> loads;

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
