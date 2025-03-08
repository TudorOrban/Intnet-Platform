package com.intnet.griddata.core.internal.in.trainingdatamanager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GridGraph {

    private Integer id;
    private ZonedDateTime createdAt;
    private GridGraphData graphData;
}
