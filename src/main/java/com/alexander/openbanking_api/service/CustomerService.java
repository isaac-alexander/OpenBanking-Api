package com.alexander.openbanking_api.service;

import com.alexander.openbanking_api.dto.customer.CustomerResponse;
import com.alexander.openbanking_api.dto.customer.UpdateCustomerRequest;

import java.util.List;

public interface CustomerService {

    CustomerResponse getCurrentCustomer();

    CustomerResponse getCustomerById(Long id);

    List<CustomerResponse> getAllCustomers();

    CustomerResponse updateCurrentCustomer(UpdateCustomerRequest request);

    void deleteCustomer(Long id);

}