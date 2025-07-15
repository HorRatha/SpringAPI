package com.example.datajpa.controller;

import com.example.datajpa.dto.CreateCustomerSegmentRequest;
import com.example.datajpa.dto.CustomerSegmentResponse;
import com.example.datajpa.service.CustomerSegmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/segment")
@RequiredArgsConstructor
public class CustomerSegmentController {
    private final CustomerSegmentService customerSegmentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerSegmentResponse createCustomerSegment(@Valid @RequestBody CreateCustomerSegmentRequest request) {
        return customerSegmentService.createCustomerSegment(request);
    }

}