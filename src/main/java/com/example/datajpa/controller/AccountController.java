package com.example.datajpa.controller;

import com.example.datajpa.dto.AccountResponse;
import com.example.datajpa.dto.CreateAccountRequest;
import com.example.datajpa.dto.CustomerPhoneRequest;
import com.example.datajpa.dto.UpdateAccountRequest;
import com.example.datajpa.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse createAccount(@Valid @RequestBody CreateAccountRequest createAccountRequest) {
        return accountService.createAccount(createAccountRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AccountResponse> findAllAccounts() {
        return accountService.findAll();
    }
    @PatchMapping("/{actNo}")
    public AccountResponse updateAccount(@PathVariable String actNo, @RequestBody UpdateAccountRequest updateAccountRequest) {
        return accountService.updateAccount(actNo, updateAccountRequest);
    }

    @PutMapping("/disable/{actNo}")
    public ResponseEntity<?> disableAccountByActNo(@PathVariable String actNo) {
        accountService.disableAccountByActNo(actNo);
        return ResponseEntity.status(HttpStatus.OK).body("Your account disable successfully");
    }

    @DeleteMapping("/{actNo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccountByActNo(@PathVariable String actNo) {
        accountService.deleteAccountByActNo(actNo);
    }

    @GetMapping("/{actNo}")
    @ResponseStatus(HttpStatus.OK)
    public AccountResponse findAccountByActNo(@PathVariable String actNo) {
        return accountService.findAccountByActNo(actNo);
    }

    @GetMapping("/by-customer-name")
    @ResponseStatus(HttpStatus.OK)
    public List<AccountResponse> findAccountByCustomer(@Valid @RequestBody CustomerPhoneRequest customerNumber) {
        return accountService.findAccountByCustomerPhone(customerNumber);
    }



}
