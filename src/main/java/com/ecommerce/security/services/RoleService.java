package com.ecommerce.security.services;

import com.ecommerce.security.repositories.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    public String createRole(String role) {
        return role;
    }
}
