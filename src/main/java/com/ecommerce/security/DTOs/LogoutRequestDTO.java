package com.ecommerce.security.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class LogoutRequestDTO {
    private UUID userId;
    private String token;
}
