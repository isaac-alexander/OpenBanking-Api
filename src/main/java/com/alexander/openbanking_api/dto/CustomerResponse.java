package com.alexander.openbanking_api.dto;

import com.alexander.openbanking_api.entity.Role;
import lombok.*;

// response returned after customer operations
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

    // email address
    private String email;

    // customer role
    private Role role;

}