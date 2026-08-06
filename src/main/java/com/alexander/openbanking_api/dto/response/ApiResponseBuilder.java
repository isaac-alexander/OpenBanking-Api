package com.alexander.openbanking_api.dto.response;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

// builds standard api responses
@Component
public class ApiResponseBuilder {

    // build normal success response
    public <T> ApiResponse<T> success(

            String message,

            T data) {

        return ApiResponse.<T>builder()

                .status("success")

                .message(message)

                .data(data)

                .build();

    }

    // build paginated response
    public <T> ApiResponse<PageResponse<T>> successPage(

            String message,

            Page<T> page) {

        PageResponse<T> response = PageResponse.<T>builder()

                .content(page.getContent())

                .page(page.getNumber())

                .size(page.getSize())

                .totalElements(page.getTotalElements())

                .totalPages(page.getTotalPages())

                .first(page.isFirst())

                .last(page.isLast())

                .build();

        return ApiResponse.<PageResponse<T>>builder()

                .status("success")

                .message(message)

                .data(response)

                .build();

    }

}