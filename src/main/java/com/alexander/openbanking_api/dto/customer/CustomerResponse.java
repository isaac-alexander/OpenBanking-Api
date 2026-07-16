package com.alexander.openbanking_api.dto.customer;

import com.alexander.openbanking_api.entity.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerResponse {

    // customer id
    private Long id;

    // first name
    private String firstName;

    // last name
    private String lastName;

    // email
    private String email;

    // phone number
    private String phoneNumber;

    // address
    private String address;

    // customer role
    private Role role;

}