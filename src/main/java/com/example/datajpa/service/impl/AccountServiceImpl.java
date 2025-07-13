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
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
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

        Customer customer = customerRepository.findByPhoneNumber(request.phoneNumber())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        // validation account type
        AccountType accountType = accountTypeRepository.findAccountTypeByTypeName(request.accountType().toUpperCase(Locale.ROOT))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account type not found"));

        String typeName = request.accountType().toUpperCase(Locale.ROOT);
        String phoneNumber = request.phoneNumber();

        if (accountRepository.existsByCustomer_PhoneAndAccountType_TypeName(phoneNumber, typeName)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Customer already has an account of this type");
        }

        Account account = accountMapper.customerRequestToAccount(request);
        account.setCustomer(customer);
        account.setAccountType(accountType);
        account.setOverLimit(BigDecimal.valueOf(10000));
        account.setIsDeleted(false);

        return accountMapper.accountToAccountResponse(accountRepository.save(account));
    }

    @Override
    public List<AccountResponse> findAll() {
        List<AccountResponse> accounts = accountRepository.findAll().stream()
                .filter(account -> account.getIsDeleted().equals(false))
                .map(accountMapper::accountToAccountResponse)
                .collect(Collectors.toList());
        // validation if list of account empty
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
    public void deleteAccountByActNo(String actNo) {
        Account deleteAccount = accountRepository.findAccountByActNo(actNo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
        accountRepository.delete(deleteAccount);

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


        if (!customerRepository.existsByPhoneNumber(customerPhoneRequest.phoneNumber())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found");
        }

        List<Account> accounts = accountRepository.findAccountByCustomer_Phone(customerPhoneRequest.phoneNumber())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));

        return accounts.stream()
                .filter(account -> account.getIsDeleted().equals(false))
                .map(accountMapper::accountToAccountResponse)
                .collect(Collectors.toList());
    }
}

