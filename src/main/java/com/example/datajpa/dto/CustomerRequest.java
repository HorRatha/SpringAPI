package com.example.datajpa.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record CustomerRequest(

        @NotBlank(message = "Customer full name is required")
        String fullName,

        @NotBlank(message = "Email is required")
        @Email
        String email,

        @NotBlank(message = "Phone number is required")
        @Size(min = 8, max = 10)
        String phone,

        @NotBlank(message = "Gender is required")
        String gender,

        @NotBlank(message = "Remark is required")
        String remark
) {
}
