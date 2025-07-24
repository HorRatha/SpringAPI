package com.example.datajpa.service.impl;

import com.example.datajpa.domain.Customer;
import com.example.datajpa.domain.CustomerSegment;
import com.example.datajpa.domain.KYC;
import com.example.datajpa.dto.CustomerRequest;
import com.example.datajpa.dto.CustomerResponse;
import com.example.datajpa.dto.UpdateCustomerRequest;
import com.example.datajpa.mapper.CustomerMapper;
import com.example.datajpa.repository.CustomerRepository;
import com.example.datajpa.repository.CustomerSegmentRepository;
import com.example.datajpa.repository.KYCRepository;
import com.example.datajpa.service.CustomerService;

import jakarta.transaction.Transactional;
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
    private final CustomerSegmentRepository customerSegmentRepository;
    private final KYCRepository kycRepository;

    @Override
    public CustomerResponse createCustomer(CustomerRequest createCustomer) {

        if(customerRepository.existsByEmail(createCustomer.email())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }

        if(customerRepository.existsByPhone(createCustomer.phone())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Phone already exists");
        }

        CustomerSegment segment = customerSegmentRepository.findBySegmentNameIgnoreCase(createCustomer.customerSegment().toUpperCase())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Segment not found"));

        Customer customer = customerMapper.customerRequestToCustomer(createCustomer);
        customer.setCustomerSegment(segment);
        customer.setIsDeleted(false);

        if (!kycRepository.existsByNationalCodeId(createCustomer.nationalCardId())){
            // auto set kyc for customer
            KYC kyc = new KYC();
            kyc.setNationalCodeId(createCustomer.nationalCardId());
            kyc.setIsVerified(false);
            kyc.setIsDeleted(false);
            kyc.setCustomer(customer);
            customer.setKyc(kyc);

            return customerMapper.customerToCustomerResponse(customerRepository.save(customer));
        }
        throw new ResponseStatusException(HttpStatus.CONFLICT, "National card already exists");

    }


    @Override
    public List<CustomerResponse> findAll() {
        List<Customer> customers = customerRepository.findAllByIsDeletedIsFalse();
        if(customers.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found");
        }
        return customers.stream()
                .map(customerMapper::customerToCustomerResponse)
                .toList();

    }

    @Override
    public CustomerResponse findByPhoneNumber(String phoneNumber) {
        return customerRepository.findByPhoneAndIsDeletedIsFalse(phoneNumber)
                .map(customerMapper::customerToCustomerResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer phone number does not exist"));
    }


    @Override
    public CustomerResponse updateByPhone(String phone, UpdateCustomerRequest updateCustomerRequest) {

        Customer customer = customerRepository.findByPhoneAndIsDeletedIsFalse(phone)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer phone number does not exist"));
        customerMapper.toCustomerPartially(updateCustomerRequest, customer);
        Customer updated = customerRepository.save(customer);
        return customerMapper.customerToCustomerResponse(updated);
    }

    @Transactional
    @Override
    public void disableByCustomerPhone(String customerPhone) {
        if(!customerRepository.existsByPhone(customerPhone)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer phone does not exist");
        }

        customerRepository.disableByCustomerPhone(customerPhone);
    }


}