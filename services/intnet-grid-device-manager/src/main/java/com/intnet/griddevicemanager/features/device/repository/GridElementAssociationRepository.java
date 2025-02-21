package com.intnet.griddevicemanager.features.device.repository;

import com.intnet.griddevicemanager.features.device.model.GridElementAssociation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GridElementAssociationRepository extends JpaRepository<GridElementAssociation, Long> {

    @Query("SELECT gea FROM GridElementAssociation gea WHERE gea.deviceId IN :deviceIds")
    List<GridElementAssociation> findByDeviceIds(List<Long> deviceIds);
}
