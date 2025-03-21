package com.intnet.griddata.features.load.model;

import com.intnet.griddata.features.bus.model.Bus;
import com.intnet.griddata.features.generator.model.GeneratorState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "loads")
public class Load {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "grid_id")
    private Long gridId;

    @ManyToOne
    @JoinColumn(name = "bus_id", nullable = false)
    private Bus bus;

    @OneToOne(mappedBy = "load", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private LoadState state;

    @Column(name = "load_name")
    private String loadName;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "load_type")
    private LoadType loadType;

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
