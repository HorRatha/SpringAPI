package com.example.datajpa.service.impl;

import com.example.datajpa.domain.Account;
import com.example.datajpa.domain.AccountType;
import com.example.datajpa.domain.Customer;
import com.example.datajpa.dto.AccountResponse;
import com.example.datajpa.dto.CreateAccountRequest;
import com.example.datajpa.dto.CustomerPhoneRequest;
import com.example.datajpa.dto.UpdateAccountRequest;
import com.example.datajpa.mapper.AccountMapper;
import com.example.datajpa.repository.AccountRepository;
import com.example.datajpa.repository.AccountTypeRepository;
import com.example.datajpa.repository.CustomerRepository;

import com.example.datajpa.service.AccountService;
import com.example.datajpa.util.CurrencyUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final AccountTypeRepository accountTypeRepository;
    private final AccountMapper accountMapper;

    @Override
    public AccountResponse createAccount(CreateAccountRequest request) {

        Account account = new Account();

        AccountType accountType = accountTypeRepository
                .findAccountTypeByTypeName(request.accountType())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Account Type Not Found"));

        Customer customer = customerRepository
                .findByPhone(request.phone())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Customer phone number not found"));


        if(customer.getKyc().getIsVerified().equals(false)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Customer need to be verified");
        }

        switch (request.actCurrency()) {
            case CurrencyUtil.USD -> {
                if (request.balance().compareTo(BigDecimal.valueOf(10)) < 0) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Balance must be greater than 10 USD");
                }

                if (customer.getCustomerSegment().getSegmentName().equals("REGULAR")) {
                    account.setOverLimit(BigDecimal.valueOf(5000));
                } else if (customer.getCustomerSegment().getSegmentName().equals("SILVER")) {
                    account.setOverLimit(BigDecimal.valueOf(10000));
                } else {
                    account.setOverLimit(BigDecimal.valueOf(50000));
                }
            }
            case CurrencyUtil.KHR -> {
                if (request.balance().compareTo(BigDecimal.valueOf(40000)) < 0) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Balance must be greater than 40,000 KHR");
                }
                if (customer.getCustomerSegment().getSegmentName().equals("REGULAR")) {
                    account.setOverLimit(BigDecimal.valueOf(5000 * 4000));
                } else if (customer.getCustomerSegment().getSegmentName().equals("SILVER")) {
                    account.setOverLimit(BigDecimal.valueOf(10000 * 4000));
                } else {
                    account.setOverLimit(BigDecimal.valueOf(50000 * 4000));
                }
            }
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Currency is not supported");
        }

        // Validate account no
        if (request.actNo() != null) {
            if (accountRepository.existsByActNo(request.actNo())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Account with Act No %s already exists", request.actNo()));
            }
            account.setActNo(request.actNo());
        } else {
            String actNo;
            do {
                actNo = String.format("%09d", new Random().nextInt(1_000_000_000));
            } while (accountRepository.existsByActNo(actNo));
            account.setActNo(actNo);
        }

        account.setActName(request.actName());
        account.setActCurrency(request.actCurrency().name());
        account.setBalance(request.balance());
        account.setIsHide(false);
        account.setIsDeleted(false);
        account.setCustomer(customer);
        account.setAccountType(accountType);

        account = accountRepository.save(account);

        return accountMapper.accountToAccountResponse(account);
    }

    @Override
    public List<AccountResponse> findAll() {
        List<AccountResponse> accounts = accountRepository.findAll().stream()
                .filter(account -> account.getIsDeleted().equals(false))
                .map(accountMapper::accountToAccountResponse)
                .collect(Collectors.toList());
        if (accounts.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
        }

        return accounts;
    }

    @Override
    public AccountResponse findAccountByActNo(String actNo) {

        return accountRepository.findAccountByActNo(actNo)
                .filter(account -> account.getIsDeleted().equals(false))
                .map(accountMapper::accountToAccountResponse)
                .orElseThrow(()  -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
    }

    @Override
    public void disableAccountByActNo(String actNo) {
        Account accountToDelete = accountRepository.findAccountByActNo(actNo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
        accountToDelete.setIsDeleted(true);

        accountRepository.save(accountToDelete);
    }


    @Override
    public AccountResponse updateAccount(String actNo, UpdateAccountRequest request) {

        Account accountToUpdate = accountRepository.findAccountByActNo(actNo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
        accountMapper.toAccountPartially(request,accountToUpdate);

        return accountMapper.accountToAccountResponse(accountRepository.save(accountToUpdate));
    }

    @Override
    public List<AccountResponse> findAccountByCustomerPhone(CustomerPhoneRequest customerPhoneRequest) {

        if (!customerRepository.existsByPhone(customerPhoneRequest.phoneNumber())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found");
        }

        List<Account> accounts = accountRepository.findAccountByCustomer_Phone(customerPhoneRequest.phoneNumber())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));

        return accounts.stream()
                .filter(account -> account.getIsDeleted().equals(false))
                .map(accountMapper::accountToAccountResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAccountByActNo(String actNo) {
        Account deleteAccount = accountRepository.findAccountByActNo(actNo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
        accountRepository.delete(deleteAccount);

    }


}