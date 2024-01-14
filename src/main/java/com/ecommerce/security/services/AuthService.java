package com.ecommerce.security.services;

import com.ecommerce.security.DTOs.*;
import com.ecommerce.security.exceptions.UserNotFoundException;
import com.ecommerce.security.models.SecurityUser;
import com.ecommerce.security.models.User;
import com.ecommerce.security.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@AllArgsConstructor
public class AuthService {

    private final AuthenticationProvider authProvider;
    private final UserRepository userRepository;
    private final JWTService jwtService;
    private PasswordEncoder passwordEncoder;


    @Transactional
    public UserResponseDTO loginUser(LoginRequestDTO requestDTO) {
        User user = userRepository
                .findByEmail(requestDTO.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User doesn't exist."));
        SecurityUser securityUser = new SecurityUser(user);
        validateUser(requestDTO, securityUser);
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("userId", securityUser.getUser().getId());
        claims.put("userName", securityUser.getUser().getId());
        claims.put("userEmail", securityUser.getUser().getId());
        claims.put("userRoles", securityUser.getUser().getRoles());
        String token = jwtService.generateToken(claims, securityUser);
        return UserResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .token(token)
                .build();
    }

    @Transactional
    public LogoutResponseDTO logoutUser(LogoutRequestDTO request) {
        User user = userRepository
                .findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User doesn't exist."));
        return LogoutResponseDTO.builder()
                .userEmail(user.getEmail())
                .message("User logged out successfully")
                .build();
    }

    @Transactional
    public UserResponseDTO signupUser(SignupRequestDTO request) {
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        User savedUser = userRepository.save(user);
        SecurityUser securityUser = new SecurityUser(savedUser);
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("userId", savedUser.getId());
        claims.put("userName", savedUser.getName());
        claims.put("userEmail", savedUser.getEmail());
        claims.put("userRoles", savedUser.getRoles());
        String token = jwtService.generateToken(claims, securityUser);

        return UserResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .token(token)
                .build();
    }

    private void validateUser(LoginRequestDTO request, SecurityUser securityUser) {
        authProvider.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));
    }
}
