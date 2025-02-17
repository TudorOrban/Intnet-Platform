package com.intnet.griddata.features.transmissionline.repository;

import com.intnet.griddata.features.transmissionline.model.TransmissionLineState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransmissionLineStateRepository extends JpaRepository<TransmissionLineState, Long> {

    Optional<TransmissionLineState> findByTransmissionLineId(Long busId);
}
