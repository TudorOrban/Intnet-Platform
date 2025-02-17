package com.intnet.gridhistory.features.bus.repository;

import com.intnet.gridhistory.features.bus.model.BusStateHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusStateHistoryRepository extends JpaRepository<BusStateHistory, Long> {

}
