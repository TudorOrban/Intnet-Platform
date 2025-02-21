package com.intnet.griddevicemanager.features.device.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "devices")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ip_address", nullable = false)
    private String ipAddress;

    @Column(name = "protocol", nullable = false)
    private String protocol;

    @Column(name = "data_structure")
    private String dataStructure;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeviceStatus status;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "device_data_config", columnDefinition = "TEXT")
    private String deviceDataConfigJson;

    @Transient
    private DeviceDataConfig deviceDataConfig;

    public DeviceDataConfig getDeviceDataConfig() {
        if (this.deviceDataConfig != null || this.deviceDataConfigJson == null) {
            return this.deviceDataConfig;
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            this.deviceDataConfig = mapper.readValue(deviceDataConfigJson, DeviceDataConfig.class);
            return this.deviceDataConfig;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Invalid device data config: " + e.getMessage()); // TODO: Add custom exceptions
        }
    }

    public void setDeviceDataConfig(DeviceDataConfig deviceDataConfig) {
        this.deviceDataConfig = deviceDataConfig;

        ObjectMapper mapper = new ObjectMapper();
        try {
            this.deviceDataConfigJson = mapper.writeValueAsString(deviceDataConfig);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Invalid device data config: " + e.getMessage());
        }
    }

    @Column(name = "metadata", columnDefinition = "JSONB")
    private String metadataJson;

    @Transient
    private DeviceMetadata metadata;

    public DeviceMetadata getMetadata() {
        if (this.metadata != null || this.metadataJson == null) {
            return this.metadata;
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            this.metadata = mapper.readValue(metadataJson, DeviceMetadata.class);
            return this.metadata;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Invalid device metadata: " + e.getMessage()); // TODO: Add custom exceptions
        }
    }

    public void setMetadata(DeviceMetadata metadata) {
        this.metadata = metadata;

        ObjectMapper mapper = new ObjectMapper();
        try {
            this.metadataJson = mapper.writeValueAsString(metadata);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Invalid device metadata: " + e.getMessage());
        }
    }

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
