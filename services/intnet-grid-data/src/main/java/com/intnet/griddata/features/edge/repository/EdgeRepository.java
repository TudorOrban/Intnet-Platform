package com.intnet.griddata.features.edge.repository;

import com.intnet.griddata.features.edge.model.Edge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EdgeRepository extends JpaRepository<Edge, Long> {

    List<Edge> findByGridId(Long gridId);

    @Query("SELECT e FROM Edge e " +
//            "LEFT JOIN FETCH e.transformers " +
            "WHERE b.gridId = :gridId")
    List<Edge> findByGridIdWithComponents(@Param("gridId") Long gridId);
}
