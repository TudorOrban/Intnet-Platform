package com.intnet.tenant.features.customrole.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCustomRoleDto {
    private String name;
    private String description;
    private List<String> policyNames;
}
