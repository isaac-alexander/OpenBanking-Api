package com.alexander.openbanking_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

// request body for customer registration
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterCustomerRequest {

    // customer's first name
    @NotBlank(message = "First name is required")
    private String firstName;

    // customer's last name
    @NotBlank(message = "Last name is required")
    private String lastName;

    // customer's email
    @Email(message = "Invalid email address")
    @NotBlank(message = "Email is required")
    private String email;

    // customer's password
    @NotBlank(message = "Password is required")
    private String password;

}