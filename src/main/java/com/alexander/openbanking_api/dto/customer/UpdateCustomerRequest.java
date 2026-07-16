package com.alexander.openbanking_api.dto.customer;

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
    @NotBlank(message = "First name is required")
    private String firstName;

    // customer's last name
    @NotBlank(message = "Last name is required")
    private String lastName;

    // customer's phone number
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    // customer's address
    @NotBlank(message = "Address is required")
    private String address;

}