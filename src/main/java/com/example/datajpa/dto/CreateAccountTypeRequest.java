package com.example.datajpa.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CreateAccountTypeRequest(
        @NotBlank(message = "Account type is required ")
        String typeName
) {
}