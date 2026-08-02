package com.alexander.openbanking_api.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

// represents the error response returned to clients
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiErrorResponse {

    // indicates whether the request succeeded
    // for errors this will always be false
    private boolean success;

    // http status code
    // example: 400, 401, 404
    private int status;

    // description of the error
    private String message;

    // date and time the error occurred
    private LocalDateTime timestamp;

}