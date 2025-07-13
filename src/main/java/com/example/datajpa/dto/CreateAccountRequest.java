package com.example.datajpa.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CreateAccountRequest(

        @NotBlank(message = "Account name is required")
        String actName,

        String actNo,

        @NotBlank(message = "Account phone number is required")
        @Size(min = 9, max = 12)
        String phoneNumber,

        @NotNull(message = "Balance is required")
        @DecimalMin(value = "5.0", message = "Minimum balance must be at least 5")
        BigDecimal balance,

        @NotBlank(message = "Account type is required")
        String accountType
) {
}
