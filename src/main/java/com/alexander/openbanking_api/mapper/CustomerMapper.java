package com.alexander.openbanking_api.mapper;

import com.alexander.openbanking_api.dto.CustomerResponse;
import com.alexander.openbanking_api.entity.Customer;
import org.springframework.stereotype.Component;

// converts customer entity into response dto
@Component
public class CustomerMapper {

    // convert customer entity to customer response
    public CustomerResponse toResponse(Customer customer) {

        return CustomerResponse.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .role(customer.getRole())
                .build();
    }

}