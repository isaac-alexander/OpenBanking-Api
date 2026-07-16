package com.alexander.openbanking_api.service;

import com.alexander.openbanking_api.dto.auth.AuthResponse;
import com.alexander.openbanking_api.dto.auth.LoginRequest;
import com.alexander.openbanking_api.dto.auth.RegisterCustomerRequest;

public interface AuthService {

    AuthResponse register(RegisterCustomerRequest request);

    AuthResponse login(LoginRequest request);

}