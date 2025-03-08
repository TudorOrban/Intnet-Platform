package com.intnet.griddata.core.internal.in.trainingdatamanager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GridGraph {

    private Long gridId;
    private GridGraphData graphData;
}
