package com.alexander.openbanking_api.controller;

import com.alexander.openbanking_api.dto.auth.AuthResponse;
import com.alexander.openbanking_api.dto.auth.LoginRequest;
import com.alexander.openbanking_api.dto.auth.RegisterCustomerRequest;
import com.alexander.openbanking_api.dto.response.ApiResponse;
import com.alexander.openbanking_api.dto.response.ApiResponseBuilder;
import com.alexander.openbanking_api.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    // authentication service
    private final AuthService authService;

    // api response builder
    private final ApiResponseBuilder responseBuilder;

    // register customer
    @PostMapping("/register")
    public ApiResponse<AuthResponse> register(
            @Valid
            @RequestBody
            RegisterCustomerRequest request) {

        AuthResponse response = authService.register(request);

        return responseBuilder.success(
                "Customer registered successfully", response);
    }

    // login customer
    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(
            @Valid
            @RequestBody
            LoginRequest request) {

        AuthResponse response = authService.login(request);

        return responseBuilder.success("Login successful", response);
    }

}