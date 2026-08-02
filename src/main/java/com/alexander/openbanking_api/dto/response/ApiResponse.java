package com.alexander.openbanking_api.dto.response;

import lombok.*;

import java.time.LocalDateTime;

// generic response wrapper
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse<T> {

    // success or error
    private String status;

    // readable message
    private String message;

    // returned object
    private T data;

    // response time
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

}