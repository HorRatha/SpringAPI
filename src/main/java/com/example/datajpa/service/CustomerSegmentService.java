package com.example.datajpa.service;

import com.example.datajpa.dto.CreateCustomerSegmentRequest;
import com.example.datajpa.dto.CustomerSegmentResponse;

public interface CustomerSegmentService {

    CustomerSegmentResponse createCustomerSegment(CreateCustomerSegmentRequest request);

}