package com.example.datajpa.service;


import com.example.datajpa.dto.CustomerRequest;
import com.example.datajpa.dto.CustomerResponse;
import com.example.datajpa.dto.UpdateCustomerRequest;

import java.util.List;
public interface CustomerService {

    CustomerResponse updateByPhone(String phone, UpdateCustomerRequest updateCustomerRequest);
    void disableByCustomerPhone(String customerPhone);
    CustomerResponse createCustomer(CustomerRequest customer);
    List<CustomerResponse> findAll();
    CustomerResponse findByPhoneNumber(String phoneNumber);


}
