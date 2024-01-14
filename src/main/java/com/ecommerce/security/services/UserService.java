package com.ecommerce.security.services;

import com.ecommerce.security.DTOs.UserResponseDTO;
import com.ecommerce.security.exceptions.UserNotFoundException;
import com.ecommerce.security.models.Role;
import com.ecommerce.security.models.SecurityUser;
import com.ecommerce.security.models.User;
import com.ecommerce.security.repositories.RoleRepository;
import com.ecommerce.security.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
@Getter
@Setter
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserResponseDTO getUserDetailsById(UUID userId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid User"));

        return  UserResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public UserResponseDTO setUserRole(UUID userId, String roleName) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with Id : " + userId + " cannot be found"));
        Role role = roleRepository
                .findByName(roleName)
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName(roleName);
                    return newRole;
                });
        user.getRoles().add(role);
        userRepository.save(user);
        return  UserResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}
