package com.intnet.tenant.features.customrole.service;

import com.intnet.tenant.features.customrole.dto.CreateCustomRoleDto;
import com.intnet.tenant.features.customrole.dto.CustomRoleResponseDto;

public interface CustomRoleService {

    CustomRoleResponseDto createCustomRole(CreateCustomRoleDto roleDto);
}
