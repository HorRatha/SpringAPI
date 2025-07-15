package com.example.datajpa.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CustomerSegmentResponse(

        String segmentName,
        BigDecimal overLimitSet,
        String description)

{ }