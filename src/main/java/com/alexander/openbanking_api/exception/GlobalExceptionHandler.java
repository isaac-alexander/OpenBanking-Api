package com.alexander.openbanking_api.exception;

import com.alexander.openbanking_api.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

// handles exceptions thrown anywhere in the application
@RestControllerAdvice
public class GlobalExceptionHandler {

    // resource not found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleNotFound(

            ResourceNotFoundException exception) {

        ApiResponse<Object> response = ApiResponse.builder()

                // request failed
                .status("error")

                // readable message
                .message(exception.getMessage())

                // no data returned
                .data(null)

                // response time
                .timestamp(LocalDateTime.now())

                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND)

                .body(response);

    }

    // invalid request
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<Object>> handleBadRequest(

            BadRequestException exception) {

        ApiResponse<Object> response = ApiResponse.builder()

                .status("error")

                .message(exception.getMessage())

                .data(null)

                .timestamp(LocalDateTime.now())

                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)

                .body(response);

    }

    // forbidden request
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Object>> handleAccessDenied(

            AccessDeniedException exception) {

        ApiResponse<Object> response = ApiResponse.builder()

                .status("error")

                .message(exception.getMessage())

                .data(null)

                .timestamp(LocalDateTime.now())

                .build();

        return ResponseEntity.status(HttpStatus.FORBIDDEN)

                .body(response);

    }

    // validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidation(

            MethodArgumentNotValidException exception) {

        Map<String, String> errors = new HashMap<>();

        // collect every validation error
        for (FieldError error : exception.getBindingResult().getFieldErrors()) {

            errors.put(

                    error.getField(),

                    error.getDefaultMessage()

            );

        }

        ApiResponse<Map<String, String>> response = ApiResponse.<Map<String, String>>builder()

                .status("error")

                .message("Validation failed")

                .data(errors)

                .timestamp(LocalDateTime.now())

                .build();

        return ResponseEntity.badRequest()

                .body(response);

    }

    // unexpected server errors
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleException(

            Exception exception) {

        ApiResponse<Object> response = ApiResponse.builder()

                .status("error")

                .message(exception.getMessage())

                .data(null)

                .timestamp(LocalDateTime.now())

                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)

                .body(response);

    }

}