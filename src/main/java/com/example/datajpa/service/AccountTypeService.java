package com.example.datajpa.service;

import com.example.datajpa.dto.AccountTypeResponse;
import com.example.datajpa.dto.CreateAccountTypeRequest;

public interface AccountTypeService {
    AccountTypeResponse createAccountType(CreateAccountTypeRequest request);
}
