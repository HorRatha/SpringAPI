package com.example.datajpa.mapper;


import com.example.datajpa.domain.Account;
import com.example.datajpa.domain.AccountType;
import com.example.datajpa.dto.AccountResponse;
import com.example.datajpa.dto.AccountTypeResponse;
import com.example.datajpa.dto.CreateAccountRequest;
import com.example.datajpa.dto.UpdateAccountRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring")

public interface AccountMapper {
    @Mapping(target = "actNo", expression = "java(java.util.UUID.randomUUID().toString())")
    @Mapping(target = "accountType", ignore = true)
    Account customerRequestToAccount(CreateAccountRequest createAccountRequest);

    @Mapping(target = "accountType", source = "account.accountType.typeName")
    AccountResponse accountToAccountResponse(Account account);

    AccountTypeResponse toAccountTypeResponse(AccountType accountType);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toAccountPartially(UpdateAccountRequest updateAccountRequest, @MappingTarget Account account);
}
