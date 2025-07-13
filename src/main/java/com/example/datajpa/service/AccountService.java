package com.example.datajpa.service;

import com.example.datajpa.dto.AccountResponse;
import com.example.datajpa.dto.CreateAccountRequest;
import com.example.datajpa.dto.CustomerPhoneRequest;
import com.example.datajpa.dto.UpdateAccountRequest;

import java.util.List;

public interface AccountService {
    AccountResponse createAccount(CreateAccountRequest request);

    List<AccountResponse> findAll();

    AccountResponse findAccountByActNo(String actNo);

    void disableAccountByActNo(String actNo);

    void deleteAccountByActNo(String actNo);

    AccountResponse updateAccount(String actNo, UpdateAccountRequest request);

    List<AccountResponse> findAccountByCustomerPhone(CustomerPhoneRequest customerPhoneRequest);
}
