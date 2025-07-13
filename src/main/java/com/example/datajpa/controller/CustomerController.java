package com.example.datajpa.controller;

import com.example.datajpa.dto.CustomerRequest;
import com.example.datajpa.dto.CustomerResponse;
import com.example.datajpa.dto.UpdateCustomerRequest;
import com.example.datajpa.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PatchMapping("/{phoneNumber}") 

    @GetMapping("/{phoneNumber}")
    public CustomerResponse getCustomerByPhoneNumber(@PathVariable String phoneNumber) {
        return customerService.findByPhoneNumber(phoneNumber);
    }

    @GetMapping
    public List<CustomerResponse> findAll() {
        return customerService.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CustomerResponse createNew(@RequestBody CustomerRequest customerRequest) {
        return customerService.createNew(customerRequest);
    }

    @PatchMapping("/{phone}")
    public CustomerResponse updateCustomer(@PathVariable String phone, @RequestBody UpdateCustomerRequest updateCustomerRequest) {
        return customerService.updateByPhoneNumber(phone, updateCustomerRequest);

    }
}
