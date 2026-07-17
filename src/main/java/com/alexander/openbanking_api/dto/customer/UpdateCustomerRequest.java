package com.alexander.openbanking_api.dto.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

// request body for updating customer information
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateCustomerRequest {

    // customer's first name
    private String firstName;

    // customer's last name
    private String lastName;

    // customer's email
    @Email(message = "Invalid email")
    private String email;

    // customer's phone number
    private String phoneNumber;

    // customer's address
    private String address;

}