package com.alexander.openbanking_api.dto;

import lombok.*;

// response returned after successful login
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {

    // jwt token
    private String token;

    // token type
    private String type;

}