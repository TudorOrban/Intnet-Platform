package com.intnet.tenant.features.customrole.controller;

import com.intnet.tenant.features.customrole.dto.CreateCustomRoleDto;
import com.intnet.tenant.features.customrole.dto.CustomRoleResponseDto;
import com.intnet.tenant.features.customrole.service.CustomRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/custom-roles")
public class CustomRoleController {

    private final CustomRoleService customRoleService;

    @Autowired
    public CustomRoleController(CustomRoleService customRoleService) {
        this.customRoleService = customRoleService;
    }

    @PostMapping
    public ResponseEntity<CustomRoleResponseDto> createCustomRole(@RequestBody CreateCustomRoleDto roleDto) {
        CustomRoleResponseDto responseDto = customRoleService.createCustomRole(roleDto);
        return ResponseEntity.ok(responseDto);
    }
}
