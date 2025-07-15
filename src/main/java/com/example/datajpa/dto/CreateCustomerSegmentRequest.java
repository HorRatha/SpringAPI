package com.example.datajpa.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CreateCustomerSegmentRequest(

        @NotBlank(message = "Segment is required")
        String segmentName,

        @NotNull(message = "overLimitSet is required")
        @Positive
        BigDecimal overLimitSet,

        String description
) { }