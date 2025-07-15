package com.example.datajpa.service.impl;

import com.example.datajpa.domain.CustomerSegment;
import com.example.datajpa.dto.CreateCustomerSegmentRequest;
import com.example.datajpa.dto.CustomerSegmentResponse;
import com.example.datajpa.mapper.CustomerSegmentMapper;
import com.example.datajpa.repository.CustomerSegmentRepository;
import com.example.datajpa.service.CustomerSegmentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerCustomerSegmentServiceImpl implements CustomerSegmentService {

    private final CustomerSegmentRepository customerSegmentRepository;
    private final CustomerSegmentMapper customerSegmentMapper;

    @Override
    public CustomerSegmentResponse createCustomerSegment(CreateCustomerSegmentRequest request) {
        if(!customerSegmentRepository.existsBySegmentName(request.segmentName())){
            CustomerSegment customerSegment = customerSegmentMapper.customerSegmentRequestToCustomerSegment(request);
            customerSegment.setIsDeleted(false);
            return customerSegmentMapper.toCustomerSegmentResponse(customerSegmentRepository.save(customerSegment));
        }
        throw new ResponseStatusException(HttpStatus.CONFLICT, "Segment already exists");
    }


}