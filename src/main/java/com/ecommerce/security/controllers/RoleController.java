package com.ecommerce.security.controllers;

import com.ecommerce.security.DTOs.CreateRoleRequestDTO;
import com.ecommerce.security.services.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;
    @PostMapping()
    public ResponseEntity<String> createRole(@RequestBody CreateRoleRequestDTO request) {
        String role = roleService.createRole(request.getRole());
        return new ResponseEntity<>(role, HttpStatus.CREATED);
    }
}