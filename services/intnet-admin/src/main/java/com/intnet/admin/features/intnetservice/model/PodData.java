package com.intnet.admin.features.intnetservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PodData {
    private String name;
    private String namespace;
    private String status;
    private String nodeName;
    private String startTime;
}
