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

    private final CustomerService  customerService;

    @GetMapping("/{phoneNumber}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerResponse findByPhone(@PathVariable String phoneNumber) {
        return customerService.findByPhoneNumber(phoneNumber);
    }

    @PatchMapping("/{phone}")
    public CustomerResponse updateCustomer(@PathVariable String phone, @RequestBody UpdateCustomerRequest updateCustomerRequest) {
        return customerService.updateByPhone(phone, updateCustomerRequest);

    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/disable-customer/{phone}")
    public void disableCustomer(@PathVariable String phone) {
        customerService.disableByCustomerPhone(phone);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponse createCustomer(@RequestBody CustomerRequest customer) {
        return customerService.createCustomer(customer);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerResponse> findAll() {
        return customerService.findAll();
    }



}
