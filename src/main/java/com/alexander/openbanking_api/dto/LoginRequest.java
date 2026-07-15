package com.alexander.openbanking_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

// request body for customer login
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest {

    // customer's email
    @Email(message = "Invalid email")
    @NotBlank(message = "Email is required")
    private String email;

    // customer's password
    @NotBlank(message = "Password is required")
    private String password;

}