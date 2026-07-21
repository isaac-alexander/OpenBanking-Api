package com.alexander.openbanking_api.dto.auth;

import lombok.*;

// response returned after successful authentication
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {

    // jwt token
    private String token;

    // authenticated customer id
    private Long customerId;

    // customer email
    private String email;

}