package com.alexander.openbanking_api.service;

import com.alexander.openbanking_api.dto.customer.CustomerResponse;
import com.alexander.openbanking_api.dto.customer.UpdateCustomerRequest;

public interface CustomerService {

    // get customer by id
    CustomerResponse getCustomerById(Long id);

    // update customer by id
    CustomerResponse updateCustomer(
            Long id,
            UpdateCustomerRequest request);

    // delete customer by id
    void deleteCustomer(Long id);

}