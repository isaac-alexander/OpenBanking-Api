package com.alexander.openbanking_api.mapper;

import com.alexander.openbanking_api.dto.customer.CustomerResponse;
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

                .phoneNumber(customer.getPhoneNumber())

                .address(customer.getAddress())

                .role(customer.getRole())

                .build();
    }

}