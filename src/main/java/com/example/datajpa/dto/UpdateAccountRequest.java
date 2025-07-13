package com.example.datajpa.dto;


import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record UpdateAccountRequest(

        String actName,

        BigDecimal balance
) { }
