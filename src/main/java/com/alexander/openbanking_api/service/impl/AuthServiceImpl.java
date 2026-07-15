package com.alexander.openbanking_api.service.impl;

import com.alexander.openbanking_api.dto.AuthResponse;
import com.alexander.openbanking_api.dto.LoginRequest;
import com.alexander.openbanking_api.dto.RegisterCustomerRequest;
import com.alexander.openbanking_api.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Override
    public AuthResponse register(RegisterCustomerRequest request) {
        return null;
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        return null;
    }

}