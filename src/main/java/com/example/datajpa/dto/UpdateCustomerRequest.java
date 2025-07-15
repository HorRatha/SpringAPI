package com.example.datajpa.dto;

import lombok.Builder;

@Builder
public record UpdateCustomerRequest(

        String fullName,
        String gender,
        String remark

) { }