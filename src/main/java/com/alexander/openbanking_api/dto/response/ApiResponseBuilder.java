package com.alexander.openbanking_api.dto.response;

import org.springframework.stereotype.Component;

// creates reusable success responses
@Component
public class ApiResponseBuilder {

    // build a success response
    public <T> ApiResponse<T> success(

            String message,

            T data) {

        return ApiResponse.<T>builder()

                .status("success")

                .message(message)

                .data(data)

                .build();

    }

}