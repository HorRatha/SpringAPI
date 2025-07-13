package com.example.datajpa.dto;

import lombok.Builder;

@Builder
public record AccountTypeResponse(
        String typeName
) {
}
