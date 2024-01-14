package com.ecommerce.security.DTOs;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogoutResponseDTO {
    private String userEmail;
    private String message;
}
