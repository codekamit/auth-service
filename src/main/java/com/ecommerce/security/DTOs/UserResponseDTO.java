package com.ecommerce.security.DTOs;

import lombok.*;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserResponseDTO {
    private UUID id;
    private String name;
    private String email;
    private String token;
}
