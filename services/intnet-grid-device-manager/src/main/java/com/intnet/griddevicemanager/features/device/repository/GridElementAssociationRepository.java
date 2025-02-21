package com.intnet.griddevicemanager.features.device.repository;

import com.intnet.griddevicemanager.features.device.model.GridElementAssociation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GridElementAssociationRepository extends JpaRepository<GridElementAssociation, Long> {

    List<GridElementAssociation> findByDeviceIds(List<Long> deviceIds);
}
