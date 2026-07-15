package com.alexander.openbanking_api.service;

import com.alexander.openbanking_api.dto.AuthResponse;
import com.alexander.openbanking_api.dto.LoginRequest;
import com.alexander.openbanking_api.dto.RegisterCustomerRequest;

public interface AuthService {

    AuthResponse register(RegisterCustomerRequest request);

    AuthResponse login(LoginRequest request);

}