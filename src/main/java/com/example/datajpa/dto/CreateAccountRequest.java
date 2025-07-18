package com.example.datajpa.dto;

import com.example.datajpa.util.CurrencyUtil;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CreateAccountRequest(

        String actNo,

        @NotBlank(message = "Account name is required")
        String actName,

        CurrencyUtil actCurrency,

        @Positive(message = "Account balance must be greater than 10$ or 40,000KHR")
        BigDecimal balance,

        @NotBlank(message = "Account type is required")
        String accountType,

        @NotBlank(message = "Customer phone number is required")
        String phone
) {
}
