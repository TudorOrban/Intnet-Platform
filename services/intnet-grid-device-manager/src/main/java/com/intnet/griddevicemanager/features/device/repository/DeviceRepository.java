package com.intnet.griddevicemanager.features.device.repository;

import com.intnet.griddevicemanager.features.device.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Long> {

}
