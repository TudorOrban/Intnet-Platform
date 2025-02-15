package com.intnet.tenant.features.customrole.dto;

import com.intnet.tenant.features.policy.model.Policy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomRoleResponseDto {
    private String name;
    private String description;
    private List<Policy> policies;
}
