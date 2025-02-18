package com.intnet.gridhistory.features.bus.repository;

import com.intnet.gridhistory.features.bus.model.BusStateRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface BusStateRecordRepository extends JpaRepository<BusStateRecord, Long> {

    List<BusStateRecord> findByBusIdAndTimeBetween(Long busId, ZonedDateTime startTime, ZonedDateTime endTime);
}
