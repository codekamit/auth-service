package com.ecommerce.security.controllers;

import com.ecommerce.security.DTOs.SetUserRolesRequestDTO;
import com.ecommerce.security.DTOs.UserResponseDTO;
import com.ecommerce.security.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("{id}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable("id") UUID userId) {
        UserResponseDTO userDTO = userService.getUserDetailsById(userId);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PostMapping("{id}/roles")
    public ResponseEntity<UserResponseDTO> setUserRoles(@PathVariable("id") UUID userId,
                                                @RequestBody SetUserRolesRequestDTO request) {
        UserResponseDTO response = userService.setUserRole(userId, request.getRole());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
