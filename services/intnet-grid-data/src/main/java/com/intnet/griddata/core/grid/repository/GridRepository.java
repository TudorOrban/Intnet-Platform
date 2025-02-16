package com.intnet.griddata.core.grid.repository;

import com.intnet.griddata.core.grid.model.Grid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GridRepository extends JpaRepository<Grid, Long> {

    boolean existsById(Long id);
}
