package com.example.datajpa.service.impl;

import com.example.datajpa.domain.Customer;
import com.example.datajpa.dto.CustomerRequest;
import com.example.datajpa.dto.CustomerResponse;
import com.example.datajpa.dto.UpdateCustomerRequest;
import com.example.datajpa.mapper.CustomerMapper;
import com.example.datajpa.repository.CustomerRepository;
import com.example.datajpa.service.CustomerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public CustomerResponse createCustomer(CustomerRequest customer) {

        if(customerRepository.existsByEmail(customer.email())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }
        if(customerRepository.existsByPhoneNumber(customer.phone())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Phone already exists");
        }


        Customer createCustomer = customerMapper.fromCreateCustomerRequest(customer);
        createCustomer.setIsDeleted(false);

        log.info("Customer before creation: {}", createCustomer.getId());
        customerRepository.save(createCustomer);
        log.info("Customer after creation: {}", createCustomer.getId());

        return customerMapper.mapCustomerToCustomerResponse(createCustomer);
    }


    @Override
    public List<CustomerResponse> findAll() {
        List<Customer> customers = customerRepository.findAll();
        if(customers.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found");
        }
        return customers.stream()
                .map(customerMapper::mapCustomerToCustomerResponse)
                .toList();

    }

    @Override
    public CustomerResponse findByPhoneNumber(String phoneNumber) {
        return customerRepository.findByPhoneNumber(phoneNumber)
                .map(customerMapper::mapCustomerToCustomerResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer phone number does not exist"));
    }


    @Override
    public CustomerResponse updateByPhone(String phone, UpdateCustomerRequest updateCustomerRequest) {

        Customer customer = customerRepository.findByPhoneNumber(phone)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer phone number does not exist"));
        customerMapper.toCustomerPartially(updateCustomerRequest, customer);
        Customer updated = customerRepository.save(customer);
        return customerMapper.mapCustomerToCustomerResponse(updated);
    }
}
