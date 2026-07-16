package com.alexander.openbanking_api.controller;

import com.alexander.openbanking_api.dto.auth.AuthResponse;
import com.alexander.openbanking_api.dto.auth.LoginRequest;
import com.alexander.openbanking_api.dto.auth.RegisterCustomerRequest;
import com.alexander.openbanking_api.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    // inject authentication service
    private final AuthService authService;

    // register customer
    @PostMapping("/register")
    public AuthResponse register(

            @Valid
            @RequestBody
            RegisterCustomerRequest request) {

        return authService.register(request);

    }

    // login customer
    @PostMapping("/login")
    public AuthResponse login(

            @Valid
            @RequestBody
            LoginRequest request) {

        return authService.login(request);

    }

}