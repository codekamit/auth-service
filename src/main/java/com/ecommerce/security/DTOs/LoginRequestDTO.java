package com.ecommerce.security.DTOs;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
public class LoginRequestDTO {
    private String email;
    private String password;
}
