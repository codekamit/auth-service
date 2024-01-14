package com.ecommerce.security.controllers;

import com.ecommerce.security.DTOs.*;
import com.ecommerce.security.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("login")
    public ResponseEntity<UserResponseDTO> login(@RequestBody LoginRequestDTO request) {
        UserResponseDTO response = authService.loginUser(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("signup")
    public ResponseEntity<UserResponseDTO> signup(@RequestBody SignupRequestDTO request) {
        UserResponseDTO response = authService.signupUser(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/logout")
    public ResponseEntity<LogoutResponseDTO> logout(@RequestBody LogoutRequestDTO request) {
        LogoutResponseDTO response = authService.logoutUser(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/validate")
    public void validate(@RequestBody ValidateTokenRequestDTO request) {

    }
}
