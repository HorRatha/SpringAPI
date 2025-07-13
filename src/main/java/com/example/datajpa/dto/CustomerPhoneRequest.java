package com.example.datajpa.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CustomerPhoneRequest(

        @NotBlank(message = "Phone number is required")
        String phoneNumber
) { }
